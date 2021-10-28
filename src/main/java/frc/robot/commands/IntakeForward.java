/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Intake;

public class IntakeForward extends Command {

    //insantiate global variables
    Intake in;
    OI oi;
	double sp;
    
    
    //constructor
	public IntakeForward(Intake intake, OI operatorInterface, double speed) {
        
        //initialize variables
        in = intake;
        oi = operatorInterface;
        sp = speed;

        //needs intake to run
        requires(in);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //set speed to run intake
        in.setIntakeSpeed(sp);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
