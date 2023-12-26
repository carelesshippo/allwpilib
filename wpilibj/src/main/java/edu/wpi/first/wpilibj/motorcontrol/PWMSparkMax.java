// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;

/**
 * REV Robotics SPARK MAX Motor Controller with PWM control.
 *
 * <p>Note that the SPARK MAX uses the following bounds for PWM values. These values should work
 * reasonably well for most controllers, but if users experience issues such as asymmetric behavior
 * around the deadband or inability to saturate the controller in either direction, calibration is
 * recommended. The calibration procedure can be found in the SPARK MAX User Manual available from
 * REV Robotics.
 *
 * <ul>
 *   <li>2.003ms = full "forward"
 *   <li>1.550ms = the "high end" of the deadband range
 *   <li>1.500ms = center of the deadband range (off)
 *   <li>1.460ms = the "low end" of the deadband range
 *   <li>0.999ms = full "reverse"
 * </ul>
 */
public class PWMSparkMax extends PWMMotorController {
  /**
   * Common initialization code called by all constructors.
   *
   * @param channel The PWM channel number. 0-9 are on-board, 10-19 are on the MXP port
   */
  @SuppressWarnings("this-escape")
  public PWMSparkMax(final int channel) {
    super("PWMSparkMax", channel);

    m_pwm.setBoundsMicroseconds(2003, 1550, 1500, 1460, 999);
    m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
    m_pwm.setSpeed(0.0);
    m_pwm.setZeroLatch();

    HAL.report(tResourceType.kResourceType_RevSparkMaxPWM, getChannel() + 1);
  }
}
