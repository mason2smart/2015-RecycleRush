package org.discobots.recyclerush.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.discobots.recyclerush.HW;
import org.discobots.recyclerush.commands.drive.TankDriveCommand;
import org.discobots.recyclerush.commands.lift.VariableLiftCommand;

/**
 *
 */
public class LiftSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	CANTalon liftMotor1, liftMotor2;
	private DigitalInput limitTop, limitBottom, positionTote;

	public LiftSubsystem() {
		liftMotor1 = new CANTalon(HW.motorLift1);
		//liftMotor2 = new CANTalon(HW.motorLift2);
		limitTop = new DigitalInput(HW.topButton);
		limitBottom = new DigitalInput(HW.bottomButton);	
	}

	public boolean getTopSwitch()
	{
		return !limitTop.get();
	}
	
	public boolean getBottomSwitch()
	{
		return !limitBottom.get();
	}
	
	public void setLiftSpeed(double liftSPD) {
		liftMotor1.set(liftSPD);
		//liftMotor2.set(liftSPD);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new VariableLiftCommand());
	}
	public void setLift(double input){
		double output = input;
		if (getTopSwitch () && output>0)
			output = 0; //sets output to zero if button is pressed
		else if (getBottomSwitch() && output <0)
			output = 0;
		liftMotor1.set(output);
		//liftMotor2.set(output);
	}

}
