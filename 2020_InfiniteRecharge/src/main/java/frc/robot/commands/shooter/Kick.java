/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Kickup;

public class Kick extends CommandBase {
  private Kickup kickup;


  double startTime;
  /**
   * Creates a new Kick.
   */
  public Kick(Kickup kickup) {
    this.kickup = kickup;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.kickup);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(Timer.getFPGATimestamp() < startTime + 0.1) {
    //   kickup.set(-kickup.getKickSpeed());
    // } else {
      kickup.set(kickup.getKickSpeed());
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    kickup.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
