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


import com.revrobotics.CANSparkMax;
/**
 * Drivetrain subsystem.
 */
public class Drivetrain extends Subsystem {

  //instantiate global variables
  private CANSparkMax frontRightController;
  private CANSparkMax frontLeftController;
  private CANSparkMax backRightController;
  private CANSparkMax backLeftController;

  public static final double DRIVETRAIN_KP = 1.875;
	public static final double DRIVETRAIN_KI = 0.006;
	public static final double DRIVETRAIN_KD = 52.5;
	public static final double DRIVETRAIN_KF = 0.15;

  //private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
  //private static final int CAN_TIMEOUT = 10;

  //constructor
  public Drivetrain() {

    //initialize variables (motor controllers with IDs)

    frontLeftController = RobotMap.DRIVE_TRAIN_LEFT_FRONT_MOTOR;
    frontRightController = RobotMap.DRIVE_TRAIN_RIGHT_FRONT_MOTOR;
    backRightController = RobotMap.DRIVE_TRAIN_BACK_RIGHT_CONTROLLER;
    backLeftController = RobotMap.DRIVE_TRAIN_BACK_LEFT_CONTROLLER;

    frontLeftController.setCANTimeout(10);
    frontRightController.setCANTimeout(10);
    backRightController.setCANTimeout(10);
    backLeftController.setCANTimeout(10);
  
    frontLeftController.setIdleMode(CANSparkMax.IdleMode.kBrake);


    //set back motor controllers to follow front motor controllers
    backLeftController.follow(frontLeftController);
    backRightController.follow(frontRightController);
  }

  //set motor controllers to percents
  public void drivePercent(double leftPercent, double rightPercent) {

    //multiply percents by forward constants
    double leftPercentForward = leftPercent * RobotMap.LEFT_DRIVETRAIN;
		double rightPercentForward = rightPercent * RobotMap.RIGHT_DRIVETRAIN;
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