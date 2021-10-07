/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
/**
 * Drivetrain subsystem.
 */
public class ShooterSecondary extends Subsystem {

  //instantiate global variables
  private TalonSRX secondary_shooter_lower;
  private TalonSRX secondary_shooter_upper;
  private static final int PID_IDX = 0;
  private static final int CAN_TIMEOUT = 10;

  //private CANSparkMax spark;

  //constructor
  public ShooterSecondary() {

    //initialize variables (motor controllers with IDs)
    // motorController = RobotMap.Indexer.INDEXER;

    secondary_shooter_lower = RobotMap.Shooter.SECONDARY_SHOOTER_LOWER;
    secondary_shooter_upper = RobotMap.Shooter.SECONDARY_SHOOTER_UPPER;

    secondary_shooter_lower.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    secondary_shooter_upper.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    // the function to flip the direction of an encoder reading 
    secondary_shooter_lower.setSensorPhase(false);
    secondary_shooter_upper.setSensorPhase(true);    
  }

  //set motor controllers to percents
 public void index(double spinPercent) {
  //TODO: forward constant, also add definition of forward and backward to OI (-1 or 1)
  // double spinPercentActual = spinPercent * 1;
  secondary_shooter_lower.set(ControlMode.PercentOutput, spinPercent * -1.0);
  secondary_shooter_upper.set(ControlMode.PercentOutput, spinPercent * -1.0);

 }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}

