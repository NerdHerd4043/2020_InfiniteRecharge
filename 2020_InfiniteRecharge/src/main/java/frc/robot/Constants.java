/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int frontLeftMotorID = 2;
        public static final int frontRightMotorID = 3;
        public static final int backLeftMotorID = 1;
        public static final int backRightMotorID = 4;

        public static final int shifterID = 1;

        //Open Loop Ramp Rate for low gear
        public static final double openLRRLow = 0.254;

        //Open Loop Ramp Rate for High gear
        public static final double openLRRHigh = 0.3;


        public static final class Gears {
            public static final boolean highGear = false;
            public static final boolean lowGear = true;
        }

        public static final class Ratios {
            public static final double highGear = 9.4;
            public static final double lowGear = 24;
        }
    }

    public static final class ClimberConstants {
        public static final int winchMotorTopID = 2;
        public static final int winchMotorBottomID = 3;

        public static final int climberSolenoidID = 0;
    }

    public static final class ShooterConstants {
        public static final int flyWheelMotorID = 5;

        public static final int conveyorMotorID = 4;
        public static final int lifterMotorID = 5;

    }

    public static final class RobotConstants {
        public static final int PCMID = 1;
    }
}
