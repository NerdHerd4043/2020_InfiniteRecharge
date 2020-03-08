/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Flywheel;

public class Shoot extends CommandBase {
  private Flywheel shooter;
  private CANPIDController pidcontroller;

  /**
   * Creates a new Shoot.
   */
  public Shoot(Flywheel shooter) {
    this.shooter = shooter;
    this.pidcontroller = this.shooter.getPidController();

    addRequirements(this.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidcontroller.setReference(shooter.getFlywheelSetPoint(), ControlType.kVelocity);

    System.out.println("Vel: " + shooter.getFlywheelVelocity());

    shooter.adjustSetPoint(RobotContainer.getDriveStick().getTriggerAxis(Hand.kRight) - RobotContainer.getDriveStick().getTriggerAxis(Hand.kLeft));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopFlywheelMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
