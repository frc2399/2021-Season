/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.pidCommands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends Command {

	private static final double VELOCITY_SCALING = .3;
	private static final double MAX_VELOCITY_CONTRIB = 0.75;
	
	private static final double MAX_VELOCITY_IN_S = 80;
	private static final double MAX_ACCELERATION_TIME_SEC = 1.25;
	private static final double MAX_ACCELERATION_DISTANCE_IN = MAX_VELOCITY_IN_S * MAX_ACCELERATION_TIME_SEC / 2;
//	private static final double SCALE = (175.0/168.0);
		
	private Timer timer;
	private Drivetrain dt;
	private AHRS navx;
	private boolean isFinished;
	
	private double coastDistance;
	private double accelerationDistance;
	private double totalDistance;
	private double distanceDirection;
	
	private double startAngle;
	
	private double p;
	private double i;
	private double d;
	private double f;
	
	private double velocityScaling;
	private double maxVelocityContrib;
	
	private double fuzz;

	
	public DriveDistance(Drivetrain dt, AHRS navx, double dist) {
		timer = new Timer();
		this.dt = dt;
		this.navx = navx;
		
		requires(dt);
		isFinished = false;	
		
		if(dist >= 0) {
			totalDistance = dist;
			distanceDirection = 1;
		} else {
			totalDistance = Math.abs(dist);
			distanceDirection = -1;
		}
		
		p = Drivetrain.DRIVETRAIN_KP;
		i = Drivetrain.DRIVETRAIN_KI;
		d = Drivetrain.DRIVETRAIN_KD;
		f = Drivetrain.DRIVETRAIN_KF;
		
		velocityScaling = VELOCITY_SCALING;
		maxVelocityContrib = MAX_VELOCITY_CONTRIB;
		
		SmartDashboard.putNumber("driveP", p);
		SmartDashboard.putNumber("driveI", i);
		SmartDashboard.putNumber("driveD", d);
		SmartDashboard.putNumber("driveF", f);
		
		SmartDashboard.putNumber("velocityScaling", velocityScaling);
		SmartDashboard.putNumber("maxVelocityContrib", maxVelocityContrib);
		
		if(totalDistance > MAX_ACCELERATION_DISTANCE_IN * 2) {
			accelerationDistance = MAX_ACCELERATION_DISTANCE_IN; 
			coastDistance = totalDistance - MAX_ACCELERATION_DISTANCE_IN * 2;
		} else {
			accelerationDistance = totalDistance / 2;
			coastDistance = 0;
		}

	}
	
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void initialize() {
		dt.enableVoltageComp();
    	dt.brakeMode();
		
		timer.start();
		startAngle = navx.getAngle();
		isFinished = false;
		fuzz = .001;
		
		p = SmartDashboard.getNumber("driveP", p);
		i = SmartDashboard.getNumber("driveI", i);
		d = SmartDashboard.getNumber("driveD", d);
		f = SmartDashboard.getNumber("driveF", f);
		
		dt.setConstants(p, i, d, f);
		// System.out.println(p);
		// System.out.println(i);
		// System.out.println(d);
		// System.out.println(f);
		
		velocityScaling = SmartDashboard.getNumber("velocityScaling", velocityScaling);
		maxVelocityContrib = SmartDashboard.getNumber("maxVelocityContrib", maxVelocityContrib);
		
		// System.out.println(maxVelocityContrib);
		// System.out.println(velocityScaling);
	}

	@Override
	protected void execute() {
		flipFuzz();
		double time = timer.get();		
		double accelerationTime = Math.sqrt(2 * accelerationDistance / (MAX_VELOCITY_IN_S / MAX_ACCELERATION_TIME_SEC));
		double coastTime = coastDistance / MAX_VELOCITY_IN_S;
		
		double beginDeceleration = accelerationTime + coastTime;
		double endTime = accelerationTime * 2 + coastTime;
		double velocity = 0;
		double relativeAngle = navx.getAngle() - startAngle;
		double velocityDifference = 0;
		
		System.out.println("Time: " + time + ", End Time: " + endTime);
		
		double relativeAngleArr[] = {relativeAngle, fuzz};
		
		SmartDashboard.putNumberArray("relativeAngle", relativeAngleArr);
		
		if (time < accelerationTime) {
			velocity = time * MAX_VELOCITY_IN_S / MAX_ACCELERATION_TIME_SEC;
		} else if (time < beginDeceleration) {
			velocity = MAX_VELOCITY_IN_S;
		} else if (time < endTime) {
			velocity = (endTime - time) * MAX_VELOCITY_IN_S / MAX_ACCELERATION_TIME_SEC;
		} else {
			velocity = 0;
			isFinished = true;
		}
		
		
		double angleRate = navx.getRate();
		double angleRateArr[] = {angleRate, fuzz};
		
		SmartDashboard.putNumberArray("angleRate", angleRateArr);
	
		velocityDifference = relativeAngle * maxVelocityContrib * Math.abs(velocity) / (MAX_VELOCITY_IN_S * velocityScaling);		
		dt.driveVelocity((velocity * distanceDirection) - (velocityDifference), (velocity * distanceDirection) + (velocityDifference));
		
		
		/**
		 * if gyro is clockwise/right angle = positive
		 * 		put more power to right, less to left
		 * 
		 * if gryo is counter clockwise/left angle = negative
		 * 		put more power to left, less to right
		 */
		
		
	}

	@Override
	protected void end() {
		timer.stop();
	}

	@Override
	protected void interrupted() {
		timer.stop();
	}

	private void flipFuzz() {
		fuzz *= -1;
	}
	
}
