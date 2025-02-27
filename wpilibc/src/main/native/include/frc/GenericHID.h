// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#pragma once

#include <stdint.h>

#include <string>

namespace frc {

class BooleanEvent;
class EventLoop;

/**
 * Handle input from standard HID devices connected to the Driver Station.
 *
 * <p>This class handles standard input that comes from the Driver Station. Each
 * time a value is requested the most recent value is returned. There is a
 * single class instance for each device and the mapping of ports to hardware
 * buttons depends on the code in the Driver Station.
 */
class GenericHID {
 public:
  enum RumbleType { kLeftRumble, kRightRumble };

  enum HIDType {
    kUnknown = -1,
    kXInputUnknown = 0,
    kXInputGamepad = 1,
    kXInputWheel = 2,
    kXInputArcadeStick = 3,
    kXInputFlightStick = 4,
    kXInputDancePad = 5,
    kXInputGuitar = 6,
    kXInputGuitar2 = 7,
    kXInputDrumKit = 8,
    kXInputGuitar3 = 11,
    kXInputArcadePad = 19,
    kHIDJoystick = 20,
    kHIDGamepad = 21,
    kHIDDriving = 22,
    kHIDFlight = 23,
    kHID1stPerson = 24
  };

  explicit GenericHID(int port);
  virtual ~GenericHID() = default;

  GenericHID(GenericHID&&) = default;
  GenericHID& operator=(GenericHID&&) = default;

  /**
   * Get the button value (starting at button 1).
   *
   * The buttons are returned in a single 16 bit value with one bit representing
   * the state of each button. The appropriate button is returned as a boolean
   * value.
   *
   * This method returns true if the button is being held down at the time
   * that this method is being called.
   *
   * @param button The button number to be read (starting at 1)
   * @return The state of the button.
   */
  bool GetRawButton(int button) const;

  /**
   * Whether the button was pressed since the last check. %Button indexes begin
   * at 1.
   *
   * This method returns true if the button went from not pressed to held down
   * since the last time this method was called. This is useful if you only
   * want to call a function once when you press the button.
   *
   * @param button The button index, beginning at 1.
   * @return Whether the button was pressed since the last check.
   */
  bool GetRawButtonPressed(int button);

  /**
   * Whether the button was released since the last check. %Button indexes begin
   * at 1.
   *
   * This method returns true if the button went from held down to not pressed
   * since the last time this method was called. This is useful if you only
   * want to call a function once when you release the button.
   *
   * @param button The button index, beginning at 1.
   * @return Whether the button was released since the last check.
   */
  bool GetRawButtonReleased(int button);

  /**
   * Constructs an event instance around this button's digital signal.
   *
   * @param button the button index
   * @param loop the event loop instance to attach the event to.
   * @return an event instance representing the button's digital signal attached
   * to the given loop.
   */
  BooleanEvent Button(int button, EventLoop* loop) const;

  /**
   * Get the value of the axis.
   *
   * @param axis The axis to read, starting at 0.
   * @return The value of the axis.
   */
  double GetRawAxis(int axis) const;

  /**
   * Get the angle in degrees of a POV on the HID.
   *
   * The POV angles start at 0 in the up direction, and increase clockwise
   * (e.g. right is 90, upper-left is 315).
   *
   * @param pov The index of the POV to read (starting at 0)
   * @return the angle of the POV in degrees, or -1 if the POV is not pressed.
   */
  int GetPOV(int pov = 0) const;

  /**
   * Get the number of axes for the HID.
   *
   * @return the number of axis for the current HID
   */
  int GetAxisCount() const;

  /**
   * Get the number of POVs for the HID.
   *
   * @return the number of POVs for the current HID
   */
  int GetPOVCount() const;

  /**
   * Get the number of buttons for the HID.
   *
   * @return the number of buttons on the current HID
   */
  int GetButtonCount() const;

  /**
   * Get if the HID is connected.
   *
   * @return true if the HID is connected
   */
  bool IsConnected() const;

  /**
   * Get the type of the HID.
   *
   * @return the type of the HID.
   */
  GenericHID::HIDType GetType() const;

  /**
   * Get the name of the HID.
   *
   * @return the name of the HID.
   */
  std::string GetName() const;

  /**
   * Get the axis type of a joystick axis.
   *
   * @return the axis type of a joystick axis.
   */
  int GetAxisType(int axis) const;

  /**
   * Get the port number of the HID.
   *
   * @return The port number of the HID.
   */
  int GetPort() const;

  /**
   * Set a single HID output value for the HID.
   *
   * @param outputNumber The index of the output to set (1-32)
   * @param value        The value to set the output to
   */
  void SetOutput(int outputNumber, bool value);

  /**
   * Set all output values for the HID.
   *
   * @param value The 32 bit output value (1 bit for each output)
   */
  void SetOutputs(int value);

  /**
   * Set the rumble output for the HID.
   *
   * The DS currently supports 2 rumble values, left rumble and right rumble.
   *
   * @param type  Which rumble value to set
   * @param value The normalized value (0 to 1) to set the rumble to
   */
  void SetRumble(RumbleType type, double value);

 private:
  int m_port;
  int m_outputs = 0;
  uint16_t m_leftRumble = 0;
  uint16_t m_rightRumble = 0;
};

}  // namespace frc
