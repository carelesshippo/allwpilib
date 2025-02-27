// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#pragma once

#include <algorithm>
#include <utility>

#include "frc/kinematics/SwerveDriveKinematics.h"
#include "units/math.h"

namespace frc {

template <class... Wheels>
SwerveDriveKinematics(Translation2d, Wheels...)
    -> SwerveDriveKinematics<1 + sizeof...(Wheels)>;

template <size_t NumModules>
wpi::array<SwerveModuleState, NumModules>
SwerveDriveKinematics<NumModules>::ToSwerveModuleStates(
    const ChassisSpeeds& chassisSpeeds,
    const Translation2d& centerOfRotation) const {
  if (chassisSpeeds.vx == 0_mps && chassisSpeeds.vy == 0_mps &&
      chassisSpeeds.omega == 0_rad_per_s) {
    for (size_t i = 0; i < NumModules; i++) {
      m_moduleStates[i].speed = 0_mps;
    }

    return m_moduleStates;
  }

  // We have a new center of rotation. We need to compute the matrix again.
  if (centerOfRotation != m_previousCoR) {
    for (size_t i = 0; i < NumModules; i++) {
      // clang-format off
      m_inverseKinematics.template block<2, 3>(i * 2, 0) =
        Matrixd<2, 3>{
          {1, 0, (-m_modules[i].Y() + centerOfRotation.Y()).value()},
          {0, 1, (+m_modules[i].X() - centerOfRotation.X()).value()}};
      // clang-format on
    }
    m_previousCoR = centerOfRotation;
  }

  Eigen::Vector3d chassisSpeedsVector{chassisSpeeds.vx.value(),
                                      chassisSpeeds.vy.value(),
                                      chassisSpeeds.omega.value()};

  Matrixd<NumModules * 2, 1> moduleStateMatrix =
      m_inverseKinematics * chassisSpeedsVector;

  for (size_t i = 0; i < NumModules; i++) {
    units::meters_per_second_t x{moduleStateMatrix(i * 2, 0)};
    units::meters_per_second_t y{moduleStateMatrix(i * 2 + 1, 0)};

    auto speed = units::math::hypot(x, y);
    Rotation2d rotation{x.value(), y.value()};

    m_moduleStates[i] = {speed, rotation};
  }

  return m_moduleStates;
}

template <size_t NumModules>
template <typename... ModuleStates>
ChassisSpeeds SwerveDriveKinematics<NumModules>::ToChassisSpeeds(
    ModuleStates&&... wheelStates) const {
  static_assert(sizeof...(wheelStates) == NumModules,
                "Number of modules is not consistent with number of wheel "
                "locations provided in constructor.");

  wpi::array<SwerveModuleState, NumModules> moduleStates{wheelStates...};

  return this->ToChassisSpeeds(moduleStates);
}

template <size_t NumModules>
ChassisSpeeds SwerveDriveKinematics<NumModules>::ToChassisSpeeds(
    wpi::array<SwerveModuleState, NumModules> moduleStates) const {
  Matrixd<NumModules * 2, 1> moduleStateMatrix;

  for (size_t i = 0; i < NumModules; ++i) {
    SwerveModuleState module = moduleStates[i];
    moduleStateMatrix(i * 2, 0) = module.speed.value() * module.angle.Cos();
    moduleStateMatrix(i * 2 + 1, 0) = module.speed.value() * module.angle.Sin();
  }

  Eigen::Vector3d chassisSpeedsVector =
      m_forwardKinematics.solve(moduleStateMatrix);

  return {units::meters_per_second_t{chassisSpeedsVector(0)},
          units::meters_per_second_t{chassisSpeedsVector(1)},
          units::radians_per_second_t{chassisSpeedsVector(2)}};
}

template <size_t NumModules>
void SwerveDriveKinematics<NumModules>::DesaturateWheelSpeeds(
    wpi::array<SwerveModuleState, NumModules>* moduleStates,
    units::meters_per_second_t attainableMaxSpeed) {
  auto& states = *moduleStates;
  auto realMaxSpeed = std::max_element(states.begin(), states.end(),
                                       [](const auto& a, const auto& b) {
                                         return units::math::abs(a.speed) <
                                                units::math::abs(b.speed);
                                       })
                          ->speed;

  if (realMaxSpeed > attainableMaxSpeed) {
    for (auto& module : states) {
      module.speed = module.speed / realMaxSpeed * attainableMaxSpeed;
    }
  }
}

}  // namespace frc
