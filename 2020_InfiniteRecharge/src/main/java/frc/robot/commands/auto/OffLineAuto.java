/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class OffLineAuto extends CommandBase {
  private final Drivetrain drivetrain;

  private double goalRots;
  private double leftOffset;
  private double rightOffset;

  /**
   * Creates a new OffLineAuto.
   */
  public OffLineAuto(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftOffset = drivetrain.getLeftRots();
    rightOffset = drivetrain.getRightRots();

    goalRots = drivetrain.DistanceToRots( 84);
    // drivetrain.shift(DriveConstants.lowGear);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(-0.5, 0);
    System.out.println("Left: " + (drivetrain.getLeftRots() - leftOffset) + " Right: " + (drivetrain.getRightRots() - rightOffset) + " Goal: " + goalRots);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drivetrain.shift(DriveConstants.highGear);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (drivetrain.getLeftRots() - leftOffset) >= goalRots && (drivetrain.getRightRots() - rightOffset) >= goalRots;
  }
}
