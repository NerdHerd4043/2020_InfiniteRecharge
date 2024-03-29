/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.drivetrain.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.climber.*;
import frc.robot.commands.auto.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private final Flywheel flywheel = new Flywheel();
  private final Climber climber = new Climber();
  private final Feeder feeder = new Feeder();
  private final Kickup kickup = new Kickup();

  private final Command autoCommand = new DriveThenAuto(drivetrain, flywheel, kickup, feeder);
  private final Command noShootAuto = new OfflineAuto(drivetrain);

  SendableChooser<Command> commandChooser = new SendableChooser<>();

  private static AHRS navxAhrs = new AHRS(SPI.Port.kMXP);

  private static XboxController driveStick = new XboxController(0);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    drivetrain.setDefaultCommand(
      // A split-stick arcade command, with forward/backward controlled by the left
      // hand, and turning controlled by the right.
      new DefaultDrive(
          drivetrain,
          () -> driveStick.getY(GenericHID.Hand.kLeft),
          () -> driveStick.getX(GenericHID.Hand.kRight)));

    commandChooser.setDefaultOption("No Shoot Auto", noShootAuto);
    commandChooser.addOption("DANGER: Shoot Auto", autoCommand);

    SmartDashboard.putData(commandChooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveStick, Button.kBack.value).whenPressed(new ClimberUp(climber, () -> { return driveStick.getTriggerAxis(GenericHID.Hand.kRight) - driveStick.getTriggerAxis(GenericHID.Hand.kLeft); }), true);
    new JoystickButton(driveStick, Button.kStart.value).toggleWhenPressed(new ClimberDown(climber, () -> { return driveStick.getTriggerAxis(GenericHID.Hand.kRight) - driveStick.getTriggerAxis(GenericHID.Hand.kLeft); }), true);

    new JoystickButton(driveStick, Button.kY.value).toggleWhenPressed(new Shoot(flywheel), true);
    new JoystickButton(driveStick, Button.kA.value).whileHeld(new Feed(feeder), true);
    new JoystickButton(driveStick, Button.kX.value).whileHeld(new Kick(kickup), true);
    new JoystickButton(driveStick, Button.kB.value).whenPressed(new ResetFlywheelAdjustment(flywheel), true);

    new JoystickButton(driveStick, Button.kStickLeft.value).whenPressed(new OpenHopper(kickup), true);
    new JoystickButton(driveStick, Button.kStickLeft.value).whenReleased(new CloseHopper(kickup), true);


    new JoystickButton(driveStick, Button.kBumperRight.value).whenPressed(new ShiftUp(drivetrain));
    new JoystickButton(driveStick, Button.kBumperLeft.value).whenPressed(new ShiftDown(drivetrain));
  }


  /**
   * @return the main driver controller
   */
  public static XboxController getDriveStick() { return driveStick; }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return commandChooser.getSelected();
  }

  /**
   * @return the drivebase subsystem
   */
  public Drivetrain getDrivetrain() {
    return drivetrain;
  }

  /**
   * @return current accumulative angle of the navx sensor
   */
  public double getAngle() {
    return navxAhrs.getAngle();
  }

  public void resetFlywheelAdjust() {
    flywheel.resetSetPointAdj();
  }
}