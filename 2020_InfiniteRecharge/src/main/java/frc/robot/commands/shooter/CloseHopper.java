/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Kickup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class CloseHopper extends InstantCommand {
  private Kickup flywheel;

  public CloseHopper(Kickup kickup) {
    this.flywheel = kickup;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.flywheel);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("C%lose");
    flywheel.setDoor(false);
  }
}
