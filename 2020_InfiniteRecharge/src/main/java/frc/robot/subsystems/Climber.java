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
  private WPI_TalonSRX winchMotorF = new WPI_TalonSRX(ClimberConstants.winchMotorTopID);  
  private WPI_TalonSRX winchMotorB = new WPI_TalonSRX(ClimberConstants.winchMotorBottomID);

  private Solenoid climberPiston = new Solenoid(RobotConstants.PCMID, ClimberConstants.climberSolenoidID);

  private boolean resting = true;
  private boolean up = false;
  

  /**
   * Creates a new Climber.
   */
  public Climber() {
    setBrakeMode(true);
    winchMotorB.follow(winchMotorF);

    climberPiston.set(false);
  }

  /**
   * Extend the climber
   */
  public void climberUp(double winchSpd) {
    climberPiston.set(true);
    winchMotorF.set(winchSpd);
    // setBrakeMode(false);
    this.setResting(false);
    this.setUp(true);
    System.out.println("Spd: " + winchSpd);
  }

  /**
   * Retract the climber
   */
  public void climberDown(double winchSpd) {
    climberPiston.set(false);
    // setBrakeMode(true);
    winchMotorF.set(winchSpd);
    this.setResting(false);
    this.setUp(false);
    System.out.println("Spd: " + winchSpd);
  }

  /**
   * Hopefully doesn't stop motor and holds arms closed during the match
   */
  public void climberRest() {
    climberPiston.set(false);
    // setBrakeMode(true);
    // winchMotorF.stopMotor();
    this.setResting(true);
    this.setUp(false);
  }

  /**
   * @param brake set to true for break mode, false for coast
   */
  public void setBrakeMode(boolean brake) {
    if(brake) {
      winchMotorF.setNeutralMode(NeutralMode.Brake);
      winchMotorB.setNeutralMode(NeutralMode.Brake);
    } else {
      winchMotorF.setNeutralMode(NeutralMode.Coast);
      winchMotorB.setNeutralMode(NeutralMode.Coast);
    }
  }

  /**
   * @param resting the resting to set
   */
  private void setResting(boolean resting) {
    this.resting = resting;
  }

  /**
   * @return the resting
   */
  public boolean isResting() {
    return resting;
  }

  /**
   * @param up the up to set
   */
  private void setUp(boolean up) {
    this.up = up;
  }

  /**
   * @return the up
   */
  public boolean isUp() {
    return up;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
