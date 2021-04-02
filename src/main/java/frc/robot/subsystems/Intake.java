/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Intake subsystem.
 */
public class Intake extends Subsystem {
  
  //instantiate motor controller
  private TalonSRX talon1;
  public VictorSPX victor1;

  public DoubleSolenoid sol;
  
  //constructor
  public Intake() {
      
    //initialize motor controller
    //talon1 = new TalonSRX(8);
    // talon1 = RobotMap.INTAKE_MOTOR;

    // victor1 = new VictorSPX(6);    
    
    //initalize double solenoids (pcm, port1, port2)
    sol = new DoubleSolenoid(RobotMap.SOLENOID_MODULE_ID, RobotMap.SOLENOID_FORWARD_CHANNEL,
        RobotMap.SOLENOID_REVERSE_CHANNEL);
  }


  //set motor speed
  public void setIntakeSpeed(double speed){
  //   talon1.set(ControlMode.PercentOutput, speed);
  }

  public void extendIntake(){
    sol.set(DoubleSolenoid.Value.kForward);
  }

  public void retractIntake(){
    sol.set(DoubleSolenoid.Value.kReverse);
  }

  //set default command
  public void initDefaultCommand(Command c) {
    setDefaultCommand(c);
  }

  @Override
  public void initDefaultCommand() {
  }
}

