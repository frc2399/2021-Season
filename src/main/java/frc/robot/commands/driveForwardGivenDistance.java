/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;

public class driveForwardGivenDistance extends Command {

    //insantiate global variables
    Drivetrain dt;
	double turnPercent, forwardPercent;
    private double startDistance;
    private double endDistance;
    
    //constructor (takes in drivetrain, left percent, and right percent)
	public driveForwardGivenDistance(Drivetrain driveTrain, double d) {
        
        //initialize variables
        dt = driveTrain;
        endDistance = d ;

        //needs drivetrain to run
        requires(dt);
        
        //set command to be interruptible
		setInterruptible(true);

    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        // startDistance = dt.getPosition();
        dt.resetPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //call drivePercent with left percent and right percent speed
        // System.out.println("Left " + leftSideSpeed);
        // System.out.println("right " + rightSideSpeed);
        dt.drivePercent(0.3, 0.3);
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
