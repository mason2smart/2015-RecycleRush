package org.discobots.recyclerush.commands;

import org.discobots.recyclerush.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	float intakeSPD;

	public IntakeCommand(float intakeSPD) {
		requires(Robot.intakeSub);
		this.intakeSPD = intakeSPD;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.intakeSub.setIntakeSpeed(intakeSPD);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
		end();
	}
}
