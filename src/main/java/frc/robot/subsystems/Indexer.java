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
public class Indexer extends Subsystem {

  //instantiate global variables
  private TalonSRX indexer_bottom;
  private TalonSRX indexer_top;
  private static final int PID_IDX = 0;
  private static final int CAN_TIMEOUT = 10;

  //private CANSparkMax spark;

  //constructor
  public Indexer() {

    //initialize variables (motor controllers with IDs)
    // motorController = RobotMap.Indexer.INDEXER;

    indexer_bottom = RobotMap.Indexer.INDEXER_LOWER;
    indexer_top = RobotMap.Indexer.INDEXER_UPPER;

    indexer_bottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    indexer_top.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_IDX, CAN_TIMEOUT);
    // the function to flip the direction of an encoder reading 
    indexer_bottom.setSensorPhase(false);
    indexer_top.setSensorPhase(true);    
  }

  //set motor controllers to percents
 public void index(double spinPercent) {
  //TODO: forward constant, also add definition of forward and backward to OI (-1 or 1)
  double spinPercentActual = spinPercent * 1;
  indexer_bottom.set(ControlMode.PercentOutput, spinPercent * -1.0);
  indexer_top.set(ControlMode.PercentOutput, spinPercent * -1.0);

 }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}

