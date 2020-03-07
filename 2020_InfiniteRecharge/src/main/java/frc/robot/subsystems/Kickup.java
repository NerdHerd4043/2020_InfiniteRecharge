/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.RobotConstants;
import edu.wpi.first.wpilibj.Solenoid;


public class Kickup extends SubsystemBase {
  private WPI_TalonSRX kickupMotor = new WPI_TalonSRX(ShooterConstants.lifterMotorID);

  private Solenoid hopper = new Solenoid(RobotConstants.PCMID, ShooterConstants.hopperSolonoidId);

  private double kickSpd;

  private final String dKickupSpeed = "Kickup Speed";

  /**
   * Creates a new Kickup.
   */
  public Kickup() {
    // Motor Speeds
    kickSpd = -0.8;

    SmartDashboard.putNumber(dKickupSpeed, kickSpd);
  }

  /**
   * Stops the kickup motor
   */
  public void stop() {
    kickupMotor.stopMotor();
  }

  /**
   * @param a the power to set the lifterMotor to
   */
  public void set(double a) {
    kickupMotor.set(a);
  }

  /**
   * @param a the state to set the hopper to
   */
  public void setDoor(boolean a) {
    hopper.set(a);
  }

  public void updatePIDValues() {
    double lift = SmartDashboard.getNumber(dKickupSpeed, 0);

    if((kickSpd != lift)) { kickSpd = lift; }
  }

  public double getKickSpeed() { return kickSpd; }
  public String getSpeedTag() { return dKickupSpeed; }

  @Override
  public void periodic() {
    updatePIDValues();
    // This method will be called once per scheduler run
  }
}
