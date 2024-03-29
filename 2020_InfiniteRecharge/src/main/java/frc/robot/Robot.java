/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.drivetrain.*;
import frc.robot.Constants.DriveConstants;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot { 
  private Command autoCommand;

  private RobotContainer robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    robotContainer = new RobotContainer();

    robotContainer.getDrivetrain()
                  .getForwardSensor()
                  .setAutomaticMode(false);

    new ShiftUp(robotContainer.getDrivetrain()).schedule();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *x
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    robotContainer.resetFlywheelAdjust();

    robotContainer.getDrivetrain()
                  .getForwardSensor()
                  .setAutomaticMode(false);
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    robotContainer.resetFlywheelAdjust();

    robotContainer.getDrivetrain()
                  .getForwardSensor()
                  .setAutomaticMode(true);
    
    autoCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (autoCommand != null) {
      autoCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    robotContainer.resetFlywheelAdjust();

    robotContainer.getDrivetrain()
                  .getForwardSensor()
                  .setAutomaticMode(true);

    robotContainer.getDrivetrain().setOpenLoopRampRate();

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    }

    // new ShiftUp(robotContainer.getDrivetrain()).schedule();
    robotContainer.getDrivetrain().shift(DriveConstants.Gears.lowGear);
    robotContainer.getDrivetrain().shift(DriveConstants.Gears.highGear);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    robotContainer.getDrivetrain()
                  .getForwardSensor()
                  .setAutomaticMode(true);
  }

  // This is a comment, please ignore.

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    if(robotContainer.getDrivetrain().getForwardSensor().isRangeValid()) 
      System.out.println(robotContainer.getDrivetrain().getForwardSensor().getRange());
    else 
      System.out.println("Invalid range");
  }
}
