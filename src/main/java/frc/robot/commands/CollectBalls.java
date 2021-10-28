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
import frc.robot.subsystems.Indexer;

public class CollectBalls extends Command {

    //insantiate global variables
    Intake in;
    OI oi;
    Indexer ind;
    
    //constructor
	public CollectBalls(Intake intake, OI operatorInterface, Indexer indexer) {
        
        //initialize variables
        in = intake;
        oi = operatorInterface;
        ind = indexer;

        //needs intake to run
        requires(in);
        requires(ind);
        
        //set command to be interruptible
		setInterruptible(true);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        in.setIntakeSpeed(1);
        ind.setIndexerSpeed(-1);
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
