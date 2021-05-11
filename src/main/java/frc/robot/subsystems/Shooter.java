/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Drivetrain subsystem.
 */
public class Shooter extends Subsystem {
  
  //instantiate motor controllers
  private TalonSRX bottom;
  private TalonSRX top;
  private TalonSRX indexer_bottom;
  private TalonSRX indexer_top;

  //TODO: correct for shooter
  private static final int PID_IDX = 0;
  private static final int CAN_TIMEOUT = 10;
  private static final int ENCODER_TICKS_PER_REVOLUTION = 4096;
  private static final double GEAR_RATIO = 84.0 / 54.0;
  private static final double TALON_100MS_IN_1S = 10.0;
  // private static final int WHEEL_DIAMETER = 6;
  // private static final double C_100MS_IN_1S = 10.0;

  public static final double SHOOTER_KP = 1.875;
	public static final double SHOOTER_KI = 0.006;
	public static final double SHOOTER_KD = 52.5;
  public static final double SHOOTER_KF = 0.15;
  
  // private double desiredBottomVelPrev, desiredTopVelPrev, actualBottomVelPrev, actualTopVelPrev;

  // private double fuzz;

  //constructor
  public Shooter() {
      
    //initialize motor controllers
    bottom = RobotMap.Shooter.SHOOTER_LOWER;
    top = RobotMap.Shooter.SHOOTER_UPPER;

    indexer_bottom = RobotMap.Indexer.INDEXER_LOWER;
    indexer_top = RobotMap.Indexer.INDEXER_UPPER;

    bottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    top.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    // the function to flip the direction of an encoder reading 
    bottom.setSensorPhase(false);
    top.setSensorPhase(true);    
    
    indexer_bottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    indexer_top.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    // the function to flip the direction of an encoder reading 
    indexer_bottom.setSensorPhase(false);
    indexer_top.setSensorPhase(true);    
    
  // fuzz = 0.001;
  }

  //ready ball


  public void setBottomIndexerSpeed(double speed){
    indexer_bottom.set(ControlMode.PercentOutput, speed * 1.0);
  }
  public void setTopIndexerSpeed(double speed){
    indexer_top.set(ControlMode.PercentOutput, speed * - 1.0);
  }


  //set bottom motor speed
  public void setBottomShooterSpeed(double speed){
    bottom.set(ControlMode.PercentOutput, speed * 1.0);
  }

  //set top motor speed
  public void setTopShooterSpeed(double speed){
    top.set(ControlMode.PercentOutput, speed * -1.0);
    if (speed != 0.0)
    {
        System.out.println("top shooter");
    }
  }

  public double toRotationsPerSecondFromNativeTalon(double talonNative) {
    return talonNative * ((Math.PI / ENCODER_TICKS_PER_REVOLUTION)) * GEAR_RATIO * TALON_100MS_IN_1S;
  }

  public double getTopRotationsPerSecond() {
    return toRotationsPerSecondFromNativeTalon(top.getSelectedSensorVelocity());
  }

  public double getBottomRotationsPerSecond() {
    return toRotationsPerSecondFromNativeTalon(bottom.getSelectedSensorVelocity());
  }

  // //TODO: correct for shooter
  // public double toInPerSecFromNativeUnits(double nativeU) {
  //   return nativeU * (WHEEL_DIAMETER * (Math.PI / ENCODER_TICKS_PER_REVOLUTION)) * GEAR_RATIO * C_100MS_IN_1S;
  // }
  
  // public double toNativeUnitsFromInPerSec(double inPerSec) {
  //   // 60.0 / 24.0
  //   return inPerSec * (ENCODER_TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)) * (1.0 / C_100MS_IN_1S);
  // }

  // public void shootVelocity(double bottomVelocity, double topVelocity) {
  //   flipFuzz();
    
  //   double desiredBottomVelocityForward = toNativeUnitsFromInPerSec(bottomVelocity) * RobotMap.Shooter.BOTTOM_SHOOTER;
  //   // double desiredTopVelocityForward = toNativeUnitsFromInPerSec(topVelocity) * RobotMap.Shooter.TOP_SHOOTER;
    
  //   bottom.set(ControlMode.Velocity, desiredBottomVelocityForward);
  //   top.set(ControlMode.Velocity, desiredTopVelocityForward);
    
  //   double actualBottomVelocityForward = bottom.getSelectedSensorVelocity(0);
  //   double actualTopVelocityForward = top.getSelectedSensorVelocity(0);
    
  //   double[] bottomVelocityArr = {desiredBottomVelocityForward, actualBottomVelocityForward, fuzz};
  //   double[] topVelocityArr = {desiredTopVelocityForward, actualTopVelocityForward, fuzz};
    
  //   SmartDashboard.putNumberArray("bottomVelocity", bottomVelocityArr);
  //   SmartDashboard.putNumberArray("topVelocity", topVelocityArr);
    
  //   double desiredBottomAccel = desiredBottomVelocityForward - desiredBottomVelPrev;
  //   double desiredTopAccel = desiredTopVelocityForward - desiredTopVelPrev;
    
  //   double actualBottomAccel = actualBottomVelocityForward - actualBottomVelPrev;
  //   double actualTopAccel = actualTopVelocityForward - actualTopVelPrev;
    
  //   double[] bottomAccelArr = {desiredBottomAccel, actualBottomAccel, fuzz};
  //   double[] topAccelArr = {desiredTopAccel, actualTopAccel, fuzz};
    
  //   SmartDashboard.putNumberArray("bottomAccel",  bottomAccelArr);
  //   SmartDashboard.putNumberArray("topAccel", topAccelArr);  
    
  //   desiredBottomVelPrev = desiredBottomVelocityForward;
  //   desiredTopVelPrev = desiredTopVelocityForward;
    
  //   actualBottomVelPrev = actualBottomVelocityForward;
  //   actualTopVelPrev = actualTopVelocityForward;
  // }

  // public void setConstants(double p, double i, double d, double f) {
		
	// 	bottom.configNominalOutputForward(0, CAN_TIMEOUT);
	// 	bottom.configNominalOutputReverse(0, CAN_TIMEOUT);
	// 	bottom.configPeakOutputForward(1, CAN_TIMEOUT);
	// 	bottom.configPeakOutputReverse(-1, CAN_TIMEOUT);

	// 	bottom.config_kF(0, f, CAN_TIMEOUT);
	// 	bottom.config_kP(0, p, CAN_TIMEOUT);
	// 	bottom.config_kI(0, i, CAN_TIMEOUT);
	// 	bottom.config_kD(0, d, CAN_TIMEOUT);
		
	// 	top.configNominalOutputForward(0, CAN_TIMEOUT);
	// 	top.configNominalOutputReverse(0, CAN_TIMEOUT);
	// 	top.configPeakOutputForward(1, CAN_TIMEOUT);
	// 	top.configPeakOutputReverse(-1, CAN_TIMEOUT);

	// 	top.config_kF(0, f, CAN_TIMEOUT);
	// 	top.config_kP(0, p, CAN_TIMEOUT);
	// 	top.config_kI(0, i, CAN_TIMEOUT);
	// 	top.config_kD(0, d, CAN_TIMEOUT);
  //   }

  // private void flipFuzz() {
  //   fuzz *= -1;
  // }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}

