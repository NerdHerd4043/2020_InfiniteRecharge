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

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive diffDrive;

  private CANSparkMax frontLeftMotor = new CANSparkMax(2, MotorType.kBrushless);;
  private CANSparkMax frontRightMotor = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax backLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);

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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
