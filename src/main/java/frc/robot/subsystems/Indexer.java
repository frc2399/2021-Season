/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Drivetrain subsystem.
 */
public class Indexer extends Subsystem {

  //instantiate global variables
  private TalonSRX upperMotorController;
  private TalonSRX lowerMotorController;

  //private CANSparkMax spark;

  //constructor
  public Indexer() {

    //initialize variables (motor controllers with IDs)
    // upperMotorController = RobotMap.INDEXER_UPPER;
    // lowerMotorController = RobotMap.INDEXER_LOWER;
    //spark = new CANSparkMax(5, MotorType.kBrushless);
  }

  //set motor controllers to percents
 public void index(double spinPercent) {
  //TODO: forward constant, also add definition of forward and backward to OI (-1 or 1)
  double spinPercentActual = spinPercent * 1;
  upperMotorController.set(ControlMode.PercentOutput, spinPercentActual);
  lowerMotorController.set(ControlMode.PercentOutput, -1 * spinPercentActual);
 }
  //public void setSpark(double percent){
    //spark.set(percent);
  //}

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}

