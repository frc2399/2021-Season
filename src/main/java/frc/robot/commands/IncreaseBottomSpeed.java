/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Shooter;

public class IncreaseBottomSpeed extends Command {

    //insantiate global variables
    Shooter shooter;
    
    //constructor
	public IncreaseBottomSpeed(Shooter sh) {
        
        //initialize variables
        shooter = sh;

        //needs intake to run
        requires(shooter);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //set speed to run intake
        shooter.increaseBottomSpeed();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true; 
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
