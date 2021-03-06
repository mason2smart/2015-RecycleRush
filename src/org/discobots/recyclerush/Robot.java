package org.discobots.recyclerush;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.discobots.recyclerush.commands.AutonomousCommand;
import org.discobots.recyclerush.commands.ChooseAutonCommand;
import org.discobots.recyclerush.subsystems.ClawSubsystem;
import org.discobots.recyclerush.subsystems.DriveTrainSubsystem;
import org.discobots.recyclerush.subsystems.ElectricalSubsystem;
import org.discobots.recyclerush.subsystems.WingSubsystem;
import org.discobots.recyclerush.subsystems.LiftSubsystem;
import org.discobots.recyclerush.subsystems.IntakeSubsystem;
import org.discobots.recyclerush.subsystems.PlowSubsystem;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static ElectricalSubsystem electricalSub;
	public static DriveTrainSubsystem driveTrainSub;
	public static LiftSubsystem liftSub;
	public static IntakeSubsystem intakeSub;
	public static WingSubsystem wingSub;
	public static PlowSubsystem plowSub;
	public static ClawSubsystem clawSub;
	public static int auton;
	public static OI oi;
	public static double totalTime;
	public static long TeleopStartTime;
	public static long loopExecutionTime = 0;
	SendableChooser autonChooser;
	Command autonomousCommand;
	Command setAuton;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// subsystem code
		autonChooser = new SendableChooser();
		autonChooser.addObject("RC FROM PRELOAD auton", new ChooseAutonCommand(5));
		autonChooser.addObject("JUST LOWER LIFT auton", new ChooseAutonCommand(6));
		autonChooser.addObject("RC FROM STEP auton", new ChooseAutonCommand(2));
		electricalSub = new ElectricalSubsystem();
		driveTrainSub = new DriveTrainSubsystem();
		liftSub	= new LiftSubsystem();
		intakeSub = new IntakeSubsystem();
		wingSub = new WingSubsystem();
		plowSub = new PlowSubsystem();
		clawSub = new ClawSubsystem();
		// gamepad code
		oi = new OI();
		// autonomous command
		//set auton command
		//two does rc bins from step; 5 does preload rc bin; default makes sure lift is down

		// dashboard init
		Dashboard.init();
		SmartDashboard.putData("Autonomous Slection", autonChooser);
		Dashboard.update();
		
		
			}

	public void disabledPeriodic() {
		long start = System.currentTimeMillis();
		SmartDashboard.putData("Autonomous Slection", autonChooser);
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
	}

	public void autonomousInit() {
		setAuton = (Command) autonChooser.getSelected();
		setAuton.start();
		autonomousCommand = new AutonomousCommand(auton);
		autonomousCommand.start();
	
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		SmartDashboard.putData("Autonomous Slection", autonChooser);
		long start = System.currentTimeMillis();
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(1);stop>System.nanoTime();) { //rumbles upon disable for 1 second
			oi.setRumble(1);
			TeleopStartTime = System.currentTimeMillis();
          }
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		if(!wingSub.isFirstTime)
		{
			wingSub.set(-1);
			
		}
		//else
		//	wingSub.set(-1);
		wingSub.isFirstTime = false;
	      for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(1);stop>System.nanoTime();) { //rumbles upon disable for 1 second
				oi.setRumble(1);
	          }
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		long start = System.currentTimeMillis();
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		long start = System.currentTimeMillis();
		LiveWindow.run();
		Scheduler.getInstance().run();
		Dashboard.update();
		long end = System.currentTimeMillis();
		loopExecutionTime = end - start;
		totalTime = (double) ((System.currentTimeMillis() - TeleopStartTime)/1000);
		
	}
}
