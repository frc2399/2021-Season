/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.pidCommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI;
import frc.robot.commands.*;
import frc.robot.pidCommands.TurnAngle.EndAngleMeaning;
import frc.robot.subsystems.*;

public class ShootBalls extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ShootBalls(Drivetrain dt, Indexer ind, Intake in, OI oi, AHRS navx, double angle, double distance) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.

    //check if goal is found (angle != NaN) -- do this in robot periodic
    //make sure there is ball + it is up against
    //turn to angle
    addSequential(new TurnAngle(dt, navx, angle, EndAngleMeaning.RELATIVE));
    //spin shooter
    //spin indexer
    addSequential(new IndexTowardsShooter(ind, oi));
    //wait
    //check if angle has changed, if changed end (make that broad) OR just turn back to angle ask driveteam
    //spin indexer to get ball up against
    //check for another ball, if not end
    //spin shooter
    //spin indexer
    addSequential(new IndexTowardsShooter(ind, oi));
    //wait
    //check if angle has changed, if changed end
    //spin indexer to get ball up against
    //check for another ball, if not end
    //spin shooter
    //spin indexer
    addSequential(new IndexTowardsShooter(ind, oi));
    //wait
    //check if angle has changed, if changed end
    //spin indexer to get ball up against
    //check for another ball, if not end
    //spin shooter
    //spin indexer
    addSequential(new IndexTowardsShooter(ind, oi));
    //wait
    //check if angle has changed, if changed end
    //spin indexer to get ball up against
    //check for another ball, if not end
    //spin shooter
    //spin indexer
    addSequential(new IndexTowardsShooter(ind, oi));
    //end

  }
}
