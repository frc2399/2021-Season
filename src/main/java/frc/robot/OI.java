/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  //instantiate global variables
  private Command defaultDrive;
  public Joystick xBox;
  Button[] xBoxButtons;
  Button[] joystickButton;
  double leftShoulder;
  double rightShoulder;
  double leftY;
  double rightY;
  double rightX;
  SlewRateLimiter filter;
  double shooterMotorSpeed;
  double slewRateLimiter;

  Joystick joystick;
  public static final double DEADBAND_WIDTH = 0.1;

  //constructor (takes drive train)
  public OI(Drivetrain dt, Shooter sh, ShooterSecondary sh_secondary, Intake in, Indexer ind) {
    
    //initialize variables
    xBox = RobotMap.OperatorInterface.XBOX;
    xBoxButtons = getButtons(xBox);

    //defaultDrive = new TankDrive(dt, this);
    defaultDrive = new KajDrive(dt, this);

    joystick = RobotMap.OperatorInterface.JOYSTICK;
    joystickButton = getButtons(joystick);
 
    System.out.println("length " + joystickButton.length);
    System.out.println("joystickButton[0] " + joystickButton[0]);

    joystickButton[1].whileHeld(new FeedShooter(sh_secondary, this, RobotMap.SecondaryShooter.FEEDSHOOTER_SPEED));
    joystickButton[3].whenPressed(new DecreaseBottomSpeed(sh));
    joystickButton[4].whenPressed(new DecreaseTopSpeed(sh));
    joystickButton[5].whenPressed(new IncreaseBottomSpeed(sh));
    joystickButton[6].whenPressed(new IncreaseTopSpeed(sh));

    joystickButton[9].whileHeld(new FeederBack(sh_secondary, this, RobotMap.SecondaryShooter.FEEDERBACK_SPEED));
    joystickButton[11].whileHeld(new IndexAwayShooter(ind, this));
    joystickButton[12].whileHeld(new IndexTowardsShooter(ind, this));

    //TODO; ShooterOff command didn't turn the shooter off
    joystickButton[10].whileHeld(new ShooterOff(sh, this));
    joystickButton[2].whileHeld(new CollectBalls(in, this, ind));

    joystickButton[7].whileHeld(new ExtendIntake(in, this));

    
  }

  public static Button[] getButtons(Joystick controller) {
    Button[] controllerButtons = new Button[controller.getButtonCount() + 1];
    System.out.println("controller.getButtonCount() " + controller.getButtonCount());
		for(int i = 1; i < controllerButtons.length; i++) {
			controllerButtons[i] = new JoystickButton(controller, i);
		}
		return controllerButtons;
	}

  public Command defaultDrive() {
		return defaultDrive;
  }

  public double getAxis(int axis) 
  {
		double val = xBox.getRawAxis(axis);
		if (Math.abs(val) <= DEADBAND_WIDTH) {
			val = 0.0;
		}
    filter = new SlewRateLimiter(getSlewRateLimiter());
    filter.calculate(val);
    val = Math.pow(val, 3);
		return val;
	}
  
  public double leftYAxis(){
    leftY = this.getAxis(1) * -1;
    return leftY;
  }

  public double rightYAxis(){
    rightY = this.getAxis(5) * -1;
    return rightY;
  }

  public double rightXAxis(){
    rightX = this.getAxis(4) * 1;
    return rightX;
  }

  public double leftShoulder(){
    leftShoulder = this.getAxis(2) * 1;
    return leftShoulder;
  }

  public double rightShoulder(){
    rightShoulder = xBox.getRawAxis(3) * 1;
    return rightShoulder;
  }

  public double getSlewRateLimiter()
  {
    slewRateLimiter = (joystick.getRawAxis(3) + 1 ) / 2. ;
    return slewRateLimiter;
  }
}
