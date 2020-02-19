/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import java.util.function.BooleanSupplier;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends CommandBase {
  private final Shooter shooter;
  private CANPIDController pidcontroller;
  private BooleanSupplier shoot;

  private double startTime;

  /**
   * Creates a new AutoShoot.
   */
  public AutoShoot(Shooter shooter, BooleanSupplier shoot) {
    this.shooter = shooter;
    this.pidcontroller = this.shooter.getPidController();
    this.shoot = shoot;

    addRequirements(this.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidcontroller.setReference(shooter.getFlywheelSetPoint(), ControlType.kVelocity);
    shooter.setConveyorMotor(shooter.getConveyorSpeed());

    if(shoot.getAsBoolean()) { shooter.setLifterMotor(shooter.getLiftSpeed()); System.out.println("KICKING IT: " + shooter.getLiftSpeed()); } else {shooter.stopKickupMotor();} 

    System.out.println("Vel: " + shooter.getFlywheelVelocity());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopFlywheelMotor();
    shooter.setConveyorMotor(0);
    shooter.setLifterMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() >= startTime + 5;
  }
}
