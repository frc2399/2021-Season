/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
/**
 * Drivetrain subsystem.
 */
public class Drivetrain extends Subsystem {

  //instantiate global variables
  // private CANSparkMax frontRightController;
  // private CANSparkMax frontLeftController;
  // private CANSparkMax backRightController;
  // private CANSparkMax backLeftController;
  private TalonSRX frontRightController;
  private TalonSRX frontLeftController;
  private TalonSRX backRightController;
  private TalonSRX backLeftController;
  //private CANSparkMax spark;

  //pidf
  private CANPIDController pidFrontLeft, pidFrontRight;
  private CANEncoder eFrontLeft, eFrontRight;

  public static final double DRIVETRAIN_KP = 1.875;
	public static final double DRIVETRAIN_KI = 0.006;
	public static final double DRIVETRAIN_KD = 52.5;
	public static final double DRIVETRAIN_KF = 0.15;

  //motor controllers
  private CANSparkMax[] allMotorControllers;

  //private static final double CLOSED_LOOP_VOLTAGE_SATURATION = 10;
  //private static final int CAN_TIMEOUT = 10;
  //TODO: correct for drivetrain
	private static final int WHEEL_DIAMETER = 6;
	private static final int ENCODER_TICKS_PER_REVOLUTION = 4096;
	private static final double GEAR_RATIO = 1.0 / 1.0;
  private static final double C_100MS_IN_1S = 10.0;

  private double desiredLeftVelPrev, desiredRightVelPrev, actualLeftVelPrev, actualRightVelPrev;
  private double fuzz;

  //constructor
  public Drivetrain() {

    //initialize variables (motor controllers with IDs)

    //TODO ids
    // frontRightController = new CANSparkMax(1, MotorType.kBrushless);
    // frontLeftController = new CANSparkMax(2, MotorType.kBrushless);
    // backRightController = new CANSparkMax(4, MotorType.kBrushless);
    // backLeftController = new CANSparkMax(3, MotorType.kBrushless);
    frontRightController = new TalonSRX(1);
    frontLeftController = new TalonSRX(2);
    backRightController = new TalonSRX(4);
    backLeftController = new TalonSRX(3);
    //spark = new CANSparkMax(5, MotorType.kBrushless);
    
    //pid
    // pidFrontRight = frontRightController.getPIDController();
    // pidFrontLeft = frontLeftController.getPIDController();
    // eFrontRight = frontRightController.getEncoder();
    // eFrontLeft = frontLeftController.getEncoder();

    //set back motor controllers to follow front motor controllers
    backLeftController.follow(frontLeftController);
    backRightController.follow(frontRightController);

    //CANSparkMax[] allMotorControllers = {frontRightController, frontLeftController, backRightController, backLeftController};

    desiredLeftVelPrev = 0;
		desiredRightVelPrev = 0;
		actualLeftVelPrev = 0;
		actualRightVelPrev = 0;
		
		fuzz = 0.001;
  }

  //set motor controllers to percents
  public void drivePercent(double leftPercent, double rightPercent) {

    //multiply percents by forward constants
    double leftPercentForward = leftPercent * RobotMap.LEFT_DRIVETRAIN;
		double rightPercentForward = rightPercent * RobotMap.RIGHT_DRIVETRAIN;
    // double leftPercentForward = leftPercent ;
		// double rightPercentForward = rightPercent ;
    
    //set motor controllers to percents
    System.out.println("left " + leftPercentForward);
    System.out.println("right " + rightPercentForward);
    frontLeftController.set(ControlMode.PercentOutput, leftPercentForward);
		frontRightController.set(ControlMode.PercentOutput, rightPercentForward);
  }


  //TODO: check these are okay
  public double toInPerSecFromNativeUnits(double nativeU) {
    return nativeU * (WHEEL_DIAMETER * (Math.PI / ENCODER_TICKS_PER_REVOLUTION)) * GEAR_RATIO * C_100MS_IN_1S;
  }
  
  public double toNativeUnitsFromInPerSec(double inPerSec) {
    // 60.0 / 24.0
    return inPerSec * (ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)) * (1.0 / C_100MS_IN_1S);
  }

  public void driveVelocity(double leftVelocity, double rightVelocity) {
    flipFuzz();
    
    // double desiredLeftVelocityForward = toNativeTalonFromInPerSec(leftVelocity) * RobotMap.Physical.DriveTrain.LEFT_FORWARD ;
    // double desiredRightVelocityForward = toNativeTalonFromInPerSec(rightVelocity) * RobotMap.Physical.DriveTrain.RIGHT_FORWARD;

    double desiredLeftVelocityForward = toNativeUnitsFromInPerSec(leftVelocity) * RobotMap.LEFT_DRIVETRAIN;
    double desiredRightVelocityForward = toNativeUnitsFromInPerSec(rightVelocity) * RobotMap.RIGHT_DRIVETRAIN;
    
    pidFrontLeft.setReference(desiredLeftVelocityForward, ControlType.kVelocity);
    pidFrontRight.setReference(desiredRightVelocityForward, ControlType.kVelocity);
    // frontLeft.set(ControlMode.Velocity, desiredLeftVelocityForward);
    // rightBackTalon.set(ControlMode.Velocity, desiredRightVelocityForward);
    
    double actualLeftVelocityForward = eFrontLeft.getVelocity();
    double actualRightVelocityForward = eFrontRight.getVelocity();

    // double actualLeftVelocityForward = leftBackTalon.getSelectedSensorVelocity(0);
    // double actualRightVelocityForward = rightBackTalon.getSelectedSensorVelocity(0);
    
    double[] leftVelocityArr = {desiredLeftVelocityForward, actualLeftVelocityForward, fuzz};
    double[] rightVelocityArr = {desiredRightVelocityForward, actualRightVelocityForward, fuzz};
    
    SmartDashboard.putNumberArray("leftVelocity", leftVelocityArr);
    SmartDashboard.putNumberArray("rightVelocity", rightVelocityArr);
    
    double desiredLeftAccel = desiredLeftVelocityForward - desiredLeftVelPrev;
    double desiredRightAccel = desiredRightVelocityForward - desiredRightVelPrev;
    
    double actualLeftAccel = actualLeftVelocityForward - actualLeftVelPrev;
    double actualRightAccel = actualRightVelocityForward - actualRightVelPrev;
    
    double[] leftAccelArr = {desiredLeftAccel, actualLeftAccel, fuzz};
    double[] rightAccelArr = {desiredRightAccel, actualRightAccel, fuzz};
    
    SmartDashboard.putNumberArray("leftAccel",  leftAccelArr);
    SmartDashboard.putNumberArray("rightAccel", rightAccelArr);  
    
    desiredLeftVelPrev = desiredLeftVelocityForward;
    desiredRightVelPrev = desiredRightVelocityForward;
    
    actualLeftVelPrev = actualLeftVelocityForward;
    actualRightVelPrev = actualRightVelocityForward;
  }

  //public void setSpark(double percent){
    //spark.set(percent);
  //}

  public void enableVoltageComp() {
    // for(CANSparkMax c : allMotorControllers) {
    //   /**
    //    * TODO: fix
    //    */
      

    //   //c.configVoltageCompSaturation(CLOSED_LOOP_VOLTAGE_SATURATION, CAN_TIMEOUT);
    //   //c.enableVoltageCompensation();
    // }
  }

  public void brakeMode() {
    for(CANSparkMax c : allMotorControllers) {
      /**
       * TODO: is idlemode the same as neutralmode?
       */
      c.setIdleMode(IdleMode.kBrake);
    }
  }

  public void setConstants(double p, double i, double d, double f) {
    //TODO: need? 
    //frontLeftController.restoreFactoryDefaults();
    
    //TODO: does this do all the things? no nominal or can timeout
    pidFrontLeft.setP(DRIVETRAIN_KP);
    pidFrontLeft.setI(DRIVETRAIN_KI);
    pidFrontLeft.setD(DRIVETRAIN_KD);
    pidFrontLeft.setFF(DRIVETRAIN_KF);
    pidFrontLeft.setOutputRange(-1, 1);

    pidFrontRight.setP(DRIVETRAIN_KP);
    pidFrontRight.setI(DRIVETRAIN_KI);
    pidFrontRight.setD(DRIVETRAIN_KD);
    pidFrontRight.setFF(DRIVETRAIN_KF);
    pidFrontRight.setOutputRange(-1, 1);
  }
  
  private void flipFuzz() {
    fuzz *= -1;
  }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}