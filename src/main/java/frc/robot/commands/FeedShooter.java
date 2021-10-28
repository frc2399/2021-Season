/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.ShooterSecondary;

public class FeedShooter extends Command {

    //insantiate global variables
    ShooterSecondary sh_secondary;
    double sp;

    OI oi;
    
    //constructor
	public FeedShooter(ShooterSecondary shooter_secondary, OI operatorInterface, double speed) {
        
        //initialize variables
        sh_secondary = shooter_secondary;
        sp = speed;
        oi = operatorInterface;

        //needs shooter to run
        requires(shooter_secondary);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //set speed to run bottom and top shooter axles
        
        sh_secondary.setLowerSecondaryShooterSpeed(-sp);
        sh_secondary.setUpperSecondaryShooterSpeed(sp);
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
