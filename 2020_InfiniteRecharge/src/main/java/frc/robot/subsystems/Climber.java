/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.RobotConstants;

public class Climber extends SubsystemBase {
  private WPI_TalonSRX winchMotorF = new WPI_TalonSRX(ClimberConstants.winchMotorForwardID);  
  private WPI_TalonSRX winchMotorB = new WPI_TalonSRX(ClimberConstants.winchMotorBackwardID);

  private Solenoid climberPiston = new Solenoid(RobotConstants.PCMID, ClimberConstants.climberSolenoidID);
  

  /**
   * Creates a new Climber.
   */
  public Climber() {
    winchMotorF.setNeutralMode(NeutralMode.Brake);
    winchMotorB.setNeutralMode(NeutralMode.Brake);
    winchMotorB.follow(winchMotorF);

    climberPiston.set(false);
  }

  /**
   * Extend the climber
   */
  public void climberUp() {
    climberPiston.set(true);
    winchMotorF.stopMotor();
  }

  /**
   * Retract the climber
   */
  public void climberDown() {
    climberPiston.set(false);
    winchMotorF.set(-0.5);
  }

  /**
   * Stop Motor and hold closed during the match
   */
  public void climberRest() {
    climberPiston.set(false);
    winchMotorF.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
