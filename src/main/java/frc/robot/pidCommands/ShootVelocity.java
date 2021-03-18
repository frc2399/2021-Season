/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.pidCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utility;
import frc.robot.subsystems.Shooter;

public class ShootVelocity extends Command {

    //insantiate global variables
    private Shooter sh;
    private Timer timer;
    private double bottom, top;
    private boolean isFinished;
    private double p, i, d, f;

    private double fuzz;
    private double time;

    //TODO: get wheels spinning to specific speed
    //TODO: get this for distance

    //constructor
	public ShootVelocity(Shooter shooter, double bottomV, double topV) {
        
        //initialize variables
        sh = shooter;
        timer = new Timer();
        bottom = bottomV;
        top = topV;

        //needs shooter to run
        requires(sh);

        isFinished = false;

        p = Shooter.SHOOTER_KP;
		i = Shooter.SHOOTER_KI;
		d = Shooter.SHOOTER_KD;
        f = Shooter.SHOOTER_KF;
        
        SmartDashboard.putNumber("shootP", p);
		SmartDashboard.putNumber("shootI", i);
		SmartDashboard.putNumber("shootD", d);
		SmartDashboard.putNumber("shootF", f);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {

        //TODO: voltagecomp and brakemode
        timer.start();
        fuzz = .001;

        p = SmartDashboard.getNumber("shootP", p);
		i = SmartDashboard.getNumber("shootI", i);
		d = SmartDashboard.getNumber("shootD", d);
		f = SmartDashboard.getNumber("shootF", f);
		
		sh.setConstants(p, i, d, f);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        flipFuzz();
        time = timer.get();
        sh.shootVelocity(bottom, top);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //TODO: bVel

        double[] temp = {};
        double[] bVel = SmartDashboard.getNumberArray("bottomVelocity", temp);
        double desBotVel = bVel[0];
        double actBotVel = bVel[1];
        double[] tVel = SmartDashboard.getNumberArray("topVelocity", temp);
        double desTopVel = tVel[0];
        double actTopVel = tVel[1];

        //TODO: tolerance
        double tolerance = 0.01;

        if(Utility.inRange(desBotVel, actBotVel, tolerance) && Utility.inRange(desTopVel, actTopVel, tolerance)){
            isFinished = true;
        } else {
            isFinished = false;
        }
        
        return isFinished; 
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

    private void flipFuzz() {
		fuzz *= -1;
	}
}
