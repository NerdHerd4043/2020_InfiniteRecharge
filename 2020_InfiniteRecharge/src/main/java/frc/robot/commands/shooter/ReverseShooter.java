/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Kickup;

public class ReverseShooter extends InstantCommand {
  Kickup kickup;
  Flywheel flywheel;
  Feeder feeder;

  public ReverseShooter(Kickup kickup, Flywheel flywheel, Feeder feeder) {
    this.kickup = kickup;
    this.flywheel = flywheel;
    this.feeder = feeder;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.kickup);
    addRequirements(this.flywheel);
    addRequirements(this.feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
}
