/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {
  private Shooter shooter;
  private CANPIDController pidcontroller;

  /**
   * Creates a new Shoot.
   */
  public Shoot(Shooter shooter) {
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
    pidcontroller.setReference(shooter.getSetPoint(), ControlType.kVelocity);
    shooter.setConveyorMotor(shooter.getConveyorSpeed());

    if(RobotContainer.getDriveStick().getAButton()) { shooter.setLifterMotor(shooter.getLiftSpeed()); }

    shooter.updatePIDValues();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pidcontroller.setReference(0, ControlType.kVelocity);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
