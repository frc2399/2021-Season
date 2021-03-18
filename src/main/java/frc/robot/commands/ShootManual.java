/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Shooter;

public class ShootManual extends Command {

    //insantiate global variables
    Shooter sh;
    OI oi;
    
    //constructor
	public ShootManual(Shooter shooter, OI operatorInterface) {
        
        //initialize variables
        sh = shooter;
        oi = operatorInterface;

        //needs shooter to run
        requires(sh);
        
        //set command to be interruptible
		//setInterruptible(true);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //set speed to run bottom and top shooter axles
        sh.setBottomShooterSpeed(oi.leftShoulder());
        sh.setTopShooterSpeed(oi.rightShoulder());
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
