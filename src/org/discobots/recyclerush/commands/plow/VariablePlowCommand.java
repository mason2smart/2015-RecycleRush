package org.discobots.recyclerush.commands.plow;

import org.discobots.recyclerush.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VariablePlowCommand extends Command {

	public VariablePlowCommand() {
		requires(Robot.plowSub);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		Robot.plowSub.setSpeedRight(-Robot.oi.getRawAnalogStickBRY()/2);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Robot.plowSub.setSpeedLeft(0);
		Robot.plowSub.setSpeedRight(0);

	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub

	}

}
