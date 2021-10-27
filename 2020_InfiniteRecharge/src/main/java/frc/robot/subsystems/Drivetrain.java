/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;
import com.revrobotics.Rev2mDistanceSensor.Unit;

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

  private Rev2mDistanceSensor forwardSensor;

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

    backLeftMotor.follow(frontLeftMotor);
    backRightMotor.follow(frontRightMotor);

    diffDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

    forwardSensor = new Rev2mDistanceSensor(Port.kOnboard);
    forwardSensor.setRangeProfile(RangeProfile.kDefault);
    forwardSensor.setDistanceUnits(Unit.kInches);
    forwardSensor.setAutomaticMode(true);

    shift(DriveConstants.Gears.highGear);
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

    setOpenLoopRampRate();
  }

  public void setOpenLoopRampRate() {
    setOpenLoopRampRate((DriveConstants.Gears.highGear != shifter.get()) ? DriveConstants.openLRRHigh : DriveConstants.openLRRLow);
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

  /**
   * @return average amount of rotations of both left motors
   */
  public double getLeftRots() {
    return -(backLeftMotor.getEncoder().getPosition() + 
            frontLeftMotor.getEncoder().getPosition()) / 2;
  }

  /**
   * @return average amount of rotations of both right motors
   */
  public double getRightRots() {
    return (backRightMotor.getEncoder().getPosition() + 
            frontRightMotor.getEncoder().getPosition()) / 2;
  }

  /**
   * @param rots Amount of rotations
   * 
   * @return estimated distance in inches in the current gear
   */
  public double RotsToDistance(double rots) { return RotsToDistance(rots, shifter.get()); }

  /**
   * @param rots Amount of rotations
   * @param gear drivetrain gear
   * 
   * @return estimated distance in inches
   */
  public double RotsToDistance(double rots, boolean gear) { 
    if (gear == DriveConstants.Gears.highGear) 
      return (Math.PI * 6 * rots) / DriveConstants.Ratios.highGear; 
    else 
      return (Math.PI * 6 * rots) / DriveConstants.Ratios.lowGear; 
  }

  /**
   * @param dist desired travel distance in rotations
   * 
   * @return estimated rotations required in the current gear
   */
  public double DistanceToRots(double dist) { return DistanceToRots(dist, shifter.get()); }

  /**
   * @param dist desired travel distance in rotations
   * @param gear drivetrain gear
   * 
   * @return estimated rotations required in the current gear
   */
  public double DistanceToRots(double dist, boolean gear) {
    if (gear == DriveConstants.Gears.highGear) 
      return (dist * DriveConstants.Ratios.highGear) / (Math.PI * 6);
    else
      return (dist * DriveConstants.Ratios.lowGear) / (Math.PI * 6);
  }

  public Rev2mDistanceSensor getForwardSensor() {
    return forwardSensor;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
