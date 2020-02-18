/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotConstants;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive diffDrive;

  private CANSparkMax frontLeftMotor = new CANSparkMax(DriveConstants.frontLeftMotorID, MotorType.kBrushless);
  private CANSparkMax frontRightMotor = new CANSparkMax(DriveConstants.frontRightMotorID, MotorType.kBrushless);
  private CANSparkMax backLeftMotor = new CANSparkMax(DriveConstants.backLeftMotorID, MotorType.kBrushless);
  private CANSparkMax backRightMotor = new CANSparkMax(DriveConstants.backRightMotorID, MotorType.kBrushless);

  private Solenoid shifter = new Solenoid(RobotConstants.PCMID, DriveConstants.shifterID);

  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    backLeftMotor.restoreFactoryDefaults();
    backRightMotor.restoreFactoryDefaults();
    frontLeftMotor.restoreFactoryDefaults();
    frontRightMotor.restoreFactoryDefaults();

    // backLeftMotor.setCANTimeout(1000);
    // backRightMotor.setCANTimeout(1000);
    // frontRightMotor.setCANTimeout(1000);
    // frontLeftMotor.setCANTimeout(1000);

    backLeftMotor.setIdleMode(IdleMode.kBrake);
    backRightMotor.setIdleMode(IdleMode.kBrake);
    frontLeftMotor.setIdleMode(IdleMode.kBrake);
    frontRightMotor.setIdleMode(IdleMode.kBrake);

    setOpenLoopRampRate(DriveConstants.openLRRHigh);

    backLeftMotor.follow(frontLeftMotor);
    backRightMotor.follow(frontRightMotor);

    diffDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    diffDrive.arcadeDrive(fwd, rot, true);
  }

  /**
   * Inverts the current gear
   */
  public void shift() { shifter.set(!shifter.get()); }

  /**
   * @param a the boolean to set the gear
   */
  public void shift(boolean a) { 
    shifter.set(a); 

    setOpenLoopRampRate((!a) ? DriveConstants.openLRRHigh : DriveConstants.openLRRLow);
  }

  /**
   * @param a the maximum rate of change in milliseconds
   */
  public void setOpenLoopRampRate(double a) {
    backLeftMotor.setOpenLoopRampRate(a);
    backRightMotor.setOpenLoopRampRate(a);
    frontLeftMotor.setOpenLoopRampRate(a);
    frontRightMotor.setOpenLoopRampRate(a);
  }

  public double getLeftRots() {
    return -(backLeftMotor.getEncoder().getPosition() + 
            frontLeftMotor.getEncoder().getPosition()) / 2;
  }

  public double getRightRots() {
    return (backRightMotor.getEncoder().getPosition() + 
            frontRightMotor.getEncoder().getPosition()) / 2;
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
