/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveThenAuto extends SequentialCommandGroup {
  /**
   * Creates a new DriveThenAuto.
   */
  public DriveThenAuto(Drivetrain drivetrain, Flywheel shooter, Kickup kickup, Feeder feeder) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new OffLineAuto(drivetrain),
          new AutoShoot(shooter, kickup, feeder, () -> { return true; }));
  }
}
