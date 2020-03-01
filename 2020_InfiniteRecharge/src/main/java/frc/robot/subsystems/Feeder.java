/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Feeder extends SubsystemBase {
  private WPI_TalonSRX feederMotor = new WPI_TalonSRX(ShooterConstants.conveyorMotorID);

  private double feedSpd;

  private final String dFeederSpeed = "Feeder Speed";

  /**
   * Creates a new Feeder.
   */
  public Feeder() {
       feedSpd = 0.8;
    
       SmartDashboard.putNumber(dFeederSpeed, feedSpd);
  }

  /**
   * @param a the power to set the conveyorMotor to
   */
  public void set(double a) {
    feederMotor.set(a);
  }

  /**
   * Stops the Feeder motor
   */
  public void stop() {
    feederMotor.stopMotor();
  }

  /**
   * takes all values from SmartDashboard, and refreshes the code values
   */
  public void updatePIDValues() {
    double feed = SmartDashboard.getNumber(dFeederSpeed, 0);

    if((feedSpd != feed)) { feedSpd = feed; }
  }

  public double getFeederSpeed() { return feedSpd; }
  public String getSpeedTag() { return dFeederSpeed; }


  @Override
  public void periodic() {
    updatePIDValues();  
    // This method will be called once per scheduler run
  }
}
