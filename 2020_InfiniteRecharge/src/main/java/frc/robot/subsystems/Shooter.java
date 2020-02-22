/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private CANSparkMax flyWheelMotor = new CANSparkMax(ShooterConstants.flyWheelMotorID, MotorType.kBrushless);

  private WPI_TalonSRX feederMotor = new WPI_TalonSRX(ShooterConstants.conveyorMotorID);
  private WPI_TalonSRX kickupMotor = new WPI_TalonSRX(ShooterConstants.lifterMotorID);

  private CANPIDController pidController;
  private CANEncoder encoder;

  // PID Variables
  public double kP, kI, kD, kIz, kFF, maxOutput, minOutput, maxRPM, feedSpd, kickSpd, setPoint;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    flyWheelMotor.restoreFactoryDefaults();

    pidController = flyWheelMotor.getPIDController();
    encoder = flyWheelMotor.getEncoder();

    // PID Coefficients
    kP = 6e-5;
    kI = 1e-6;
    kD = 1e-3;
    kIz = 1e-1;
    kFF = -0.000015;
    maxOutput = 1;
    minOutput = -1; 
    maxRPM = 5700;
    setPoint = -7000;

    // Motor Speeds
    kickSpd = 0.8;
    feedSpd = -0.8;

    // set PID coeffecients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(minOutput, maxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", maxOutput);
    SmartDashboard.putNumber("Min Output", minOutput);

    SmartDashboard.putNumber("Set Point", setPoint);
    SmartDashboard.putNumber("Conveyor Speed", feedSpd);
    SmartDashboard.putNumber("Lifter Speed", kickSpd);

    SmartDashboard.putNumber("Flywheel Velocity", getFlywheelVelocity());
  }

  /**
   * @param a the power to set the conveyorMotor to
   */
  public void setFeederMotor(double a) {
    feederMotor.set(a);
  }

  /**
   * @param a the power to set the lifterMotor to
   */
  public void setKickupMotor(double a) {
    kickupMotor.set(a);
  }

  /**
   * Stops the flywheel motor
   */
  public void stopFlywheelMotor() {
    flyWheelMotor.stopMotor();
  }

  /**
   * Stops the kickup motor
   */
  public void stopKickupMotor() {
    kickupMotor.stopMotor();
  }

  /**
   * Stops the Feeder motor
   */
  public void stopFeederMotor() {
    feederMotor.stopMotor();
  }

  /**
   * takes all values from SmartDashboard, and refreshes the code values
   */
  public void updatePIDValues() {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    double conv = SmartDashboard.getNumber("Conveyor Speed", 0);
    double lift = SmartDashboard.getNumber("Lifter Speed", 0);

    double dSetPoint = SmartDashboard.getNumber("Set Point", 0);

    double vel = SmartDashboard.getNumber("Flywheel Velocity", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { pidController.setP(p); kP = p; }
    if((i != kI)) { pidController.setI(i); kI = i; }
    if((d != kD)) { pidController.setD(d); kD = d; }
    if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { pidController.setFF(ff); kFF = ff; }
    if((max != maxOutput) || (min != minOutput)) { 
      pidController.setOutputRange(min, max); 
      minOutput = min; maxOutput = max; 
    }

    if((feedSpd != conv)) { feedSpd = conv; }
    if((kickSpd != lift)) { kickSpd = lift; }
 
    if((dSetPoint != setPoint)) { setPoint = dSetPoint; }

    if(vel != getFlywheelVelocity()){ SmartDashboard.putNumber("Flywheel Velocity", getFlywheelVelocity()); }
  }

  public double getFlywheelVelocity() { return encoder.getVelocity(); }
  public double getFlywheelPos() { return encoder.getPosition(); }
  public double getFlywheelSetPoint() { return setPoint; }
  public double getLiftSpeed() { return kickSpd; }
  public double getConveyorSpeed() { return feedSpd; }

  /**
   * @return the pidController
   */
  public CANPIDController getPidController() {
    return pidController;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updatePIDValues();
  }
}
