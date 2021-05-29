/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.RobotBase;
// import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;


import com.revrobotics.CANSparkMax;
/**
 * Drivetrain subsystem.
 */
public class Drivetrain extends Subsystem {

  //instantiate global variables
  public CANSparkMax frontRightController;
  public CANSparkMax frontLeftController;
  private CANSparkMax backRightController;
  private CANSparkMax backLeftController;

  private CANEncoder left_encoder;
  private CANEncoder right_encoder;
  

  public static final double DRIVETRAIN_KP = 1.875;
	public static final double DRIVETRAIN_KI = 0.006;
	public static final double DRIVETRAIN_KD = 52.5;
	public static final double DRIVETRAIN_KF = 0.15;

  //private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
  //private static final int CAN_TIMEOUT = 10;

  //constructor
  public Drivetrain() {

    //initialize variables (motor controllers with IDs)

    frontLeftController = RobotMap.DriveTrain.DRIVE_TRAIN_LEFT_FRONT_MOTOR;
    frontRightController = RobotMap.DriveTrain.DRIVE_TRAIN_RIGHT_FRONT_MOTOR;
    backRightController = RobotMap.DriveTrain.DRIVE_TRAIN_BACK_RIGHT_CONTROLLER;
    backLeftController = RobotMap.DriveTrain.DRIVE_TRAIN_BACK_LEFT_CONTROLLER;

    frontLeftController.setCANTimeout(10);
    frontRightController.setCANTimeout(10);
    backRightController.setCANTimeout(10);
    backLeftController.setCANTimeout(10);
  
    //frontLeftController.setIdleMode(CANSparkMax.IdleMode.kBrake);


    //set back motor controllers to follow front motor controllers
    backLeftController.follow(frontLeftController);
    backRightController.follow(frontRightController);


    left_encoder = frontLeftController.getEncoder();
    right_encoder = frontRightController.getEncoder();
  

    double left_velocity = left_encoder.getVelocity(); // native RPM
    double right_velocity = right_encoder.getVelocity(); // native RPM

  }

  public void resetPosition(){
    left_encoder.setPosition(0.0);
    right_encoder.setPosition(0.0);
  }

  public double getPosition()
  {
    double right_position = right_encoder.getPosition(); // number of rotations of the motor
    double left_position = right_encoder.getPosition(); // number of rotations of the motor
    return ( (left_position + right_position) / 2.0);
  }

  //set motor controllers to percents
  public void drivePercent(double leftPercent, double rightPercent) {

    //multiply percents by forward constants
    double leftPercentForward = leftPercent * RobotMap.DriveDirection.LEFT_DRIVETRAIN;
		double rightPercentForward = rightPercent * RobotMap.DriveDirection.RIGHT_DRIVETRAIN;
    // double leftPercentForward = leftPercent ;
		// double rightPercentForward = rightPercent ;
    
    
    //set motor controllers to percents
    // System.out.println("left " + leftPercentForward);
    // System.out.println("right " + rightPercentForward);
    frontLeftController.set(leftPercentForward);
		frontRightController.set(rightPercentForward);
    SmartDashboard.putNumber("left ", leftPercentForward);
    SmartDashboard.putNumber("right ", rightPercentForward);
  }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}