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

public class Kickup extends SubsystemBase {
  private WPI_TalonSRX kickupMotor = new WPI_TalonSRX(ShooterConstants.lifterMotorID);

  private double kickSpd;

  /**
   * Creates a new Kickup.
   */
  public Kickup() {
    // Motor Speeds
    kickSpd = 0.8;

    SmartDashboard.putNumber("Kickup Speed", kickSpd);
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

  public void updatePIDValues() {
    double lift = SmartDashboard.getNumber("Kickup Speed", 0);

    if((kickSpd != lift)) { kickSpd = lift; }
  }

  public double getKickSpeed() { return kickSpd; }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
