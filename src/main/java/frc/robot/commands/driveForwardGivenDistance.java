/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;

public class driveForwardGivenDistance extends Command {

    //insantiate global variables
    Drivetrain dt;
	double turnPercent, forwardPercent;
    private double endDistance;

    private double speed;
    
    //constructor (takes in drivetrain, left percent, and right percent)
	public driveForwardGivenDistance(Drivetrain driveTrain, double d, double s) {
        
        //initialize variables
        dt = driveTrain;
        endDistance = d ;
        speed = s;

        //needs drivetrain to run
        requires(dt);
        
        //set command to be interruptible
		setInterruptible(true);

    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        dt.resetPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        dt.drivePercent(speed, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (dt.getPosition() >= endDistance);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
