// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#pragma once

#include <memory>
#include <span>

#include <imgui.h>
#include <networktables/DoubleArrayTopic.h>
#include <networktables/FloatArrayTopic.h>
#include <networktables/MultiSubscriber.h>
#include <networktables/NetworkTableInstance.h>
#include <networktables/NetworkTableListener.h>
#include <networktables/RawTopic.h>
#include <ntcore_cpp.h>
#include <wpi/struct/DynamicStruct.h>

#include "glass/other/Canvas2D.h"

namespace glass {

class NTCanvas2DModel : public Canvas2DModel {
 public:
  static constexpr const char* kType = "Canvas2d";

  explicit NTCanvas2DModel(std::string_view path,
                           wpi::StructDescriptorDatabase& structDatabase);
  NTCanvas2DModel(nt::NetworkTableInstance inst,
                  wpi::StructDescriptorDatabase& structDatabase,
                  std::string_view path);
  ~NTCanvas2DModel() override;

  const char* GetPath() const { return m_path.c_str(); }

  void Update() override;
  bool Exists() override;
  bool IsReadOnly() override;

  std::vector<Canvas2DLine> GetLines() const override;
  ImVec2 GetDimensions() const override;

 private:
  std::string m_path;
  nt::NetworkTableInstance m_inst;
  wpi::StructDescriptorDatabase& m_structDatabase;

  nt::FloatArraySubscriber m_dimensionsSub;
  nt::RawSubscriber m_linesSub;

  ImVec2 m_dimensions;
  std::vector<Canvas2DLine> m_lines;
};
}  // namespace glass
