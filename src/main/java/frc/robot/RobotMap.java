/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  public interface DriveDirection {

    public static final int LEFT_DRIVETRAIN = 1;
    public static final int RIGHT_DRIVETRAIN = -1;
  }

  public interface OperatorInterface {
    public static Joystick JOYSTICK = new Joystick(1);
    public static Joystick XBOX = new Joystick(0);

  }


  public interface Shooter{
    public static TalonSRX SHOOTER_UPPER =  new TalonSRX(6);
    public static TalonSRX SHOOTER_LOWER = new TalonSRX(5);
  }

  public interface Indexer {

    public static TalonSRX INDEXER_UPPER = new TalonSRX(7);
    public static TalonSRX INDEXER_LOWER = new TalonSRX(9);
  }

  public interface DriveTrain {

    public static CANSparkMax DRIVE_TRAIN_LEFT_FRONT_MOTOR =  new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax DRIVE_TRAIN_RIGHT_FRONT_MOTOR = new CANSparkMax(3, MotorType.kBrushless);
    public static CANSparkMax DRIVE_TRAIN_BACK_RIGHT_CONTROLLER = new CANSparkMax(4, MotorType.kBrushless);
    public static CANSparkMax DRIVE_TRAIN_BACK_LEFT_CONTROLLER = new CANSparkMax(2, MotorType.kBrushless);
  }
  


  // public static TalonSRX INTAKE_MOTOR = new TalonSRX(8);
  
  

  public interface Intake {
    public static DoubleSolenoid SOL = new DoubleSolenoid(1, 0, 1);

  }
  

  // public static int SOLENOID_MODULE_ID = 1;
  // public static int SOLENOID_FORWARD_CHANNEL = 0;
  // public static int SOLENOID_REVERSE_CHANNEL = 3;
}
