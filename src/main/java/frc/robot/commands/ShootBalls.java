package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.Drivetrain;


public class ShootBalls extends CommandGroup{

    public ShootBalls(Drivetrain dt){
        // Command autonomousCommand;
        // Drivetrain driveTrain ;
        // autonomousCommand = new driveBackwardForOneSecond(driveTrain);

        addSequential(new driveBackwardForOneSecond(dt));
    }
    
}
