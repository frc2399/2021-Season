/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.IntakeDefault;
import frc.robot.commands.driveForwardForOneSecond;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.*;
// import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.ShootDefault;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  //instantiate global variables
  public static Drivetrain dt;
  public static Shooter sh;
  public static OI oi;
  public static Intake in;
  public static Indexer ind;
  Command autonomousCommand;

  NetworkTableEntry xAng, dist;
  double xAngle, distance;

  //AHRS navx = new AHRS(SerialPort.Port.kUSB);

  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
    //initialize variables
    dt = new Drivetrain();
    sh = new Shooter();
    in = new Intake();
    // ind = new Indexer();
    oi = new OI(dt, sh, in, ind);

    //set initial default command for drive train to default drive
    dt.initDefaultCommand(oi.defaultDrive());

    //set default command for shooter to default shoot
    sh.initDefaultCommand(new ShootDefault(sh, oi));
    
    //set default command for intake to default shoot
    in.initDefaultCommand(new IntakeDefault(in, oi));

    // NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // NetworkTable table = inst.getTable("database");

    // instantiate the command used for the autonomous period
    autonomousCommand = new driveForwardForOneSecond(dt);

    //PowerDistributionPanel example = new PowerDistributionPanel(0);

    //Compressor c = new Compressor(0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    // schedule the autonomous command (example)
    if (autonomousCommand != null) autonomousCommand.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    
    // xAngle = xAng.getDouble(1);
    // distance = dist.getDouble(1);


  }

    @Override
    public void testInit() {
        teleopInit();
    }

/**
   * This function is called periodically during test mode.
   */

    @Override
    public void testPeriodic() {
        teleopPeriodic();
    }
}
