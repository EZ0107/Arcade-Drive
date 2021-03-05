// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
    // Drive with arcade drive.
  private final WPI_TalonFX m_leftfrontMotor = new WPI_TalonFX(3);
  private final WPI_TalonFX m_leftrearMotor = new WPI_TalonFX(2);
  private final WPI_TalonFX m_rightrearMotor = new WPI_TalonFX(1);
  private final WPI_TalonFX m_rightfrontMotor = new WPI_TalonFX(0);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftfrontMotor, m_rightfrontMotor);
  private final XboxController m_stick = new XboxController(0);
  private final Solenoid s_shifter = new Solenoid(0);
  private int DriveMode = 0;

  //Operator
  private final XboxController Operator = new XboxController(1);
  private final VictorSPX Threadmill = new VictorSPX(0); 
  private final VictorSPX TopMotor = new VictorSPX(1);
  private final VictorSPX BottomMotor = new VictorSPX(2);
  private final VictorSPX Intake =  new VictorSPX(3);
  
  @Override
  public void teleopInit() {
    m_leftrearMotor.follow(m_leftfrontMotor);
    m_rightrearMotor.follow(m_rightfrontMotor);
  }

  @Override
  public void robotPeriodic() {
    boolean b_start = m_stick.getStartButton();
    boolean b_back = m_stick.getBackButton();
    
    if (b_back && (DriveMode == 1)) {
      DriveMode = 0;
    } else if (b_start && (DriveMode == 0)) {
      DriveMode = 1;
    }
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(acceleration(DriveMode), m_stick.getX()*-1);

    
    
    boolean b_left_bumper = m_stick.getBumper(GenericHID.Hand.kLeft);
    boolean b_right_bumper = m_stick.getBumper(GenericHID.Hand.kRight);
    boolean state_shifter = s_shifter.get();
    boolean o_right_bumper = Operator.getBumper(GenericHID.Hand.kRight);
    boolean intake_a = Operator.getAButton();

    if (intake_a == true) {
      Intake.set(ControlMode.PercentOutput, 0.2);
    }
    else if (intake_a == false){
      Intake.set(ControlMode.PercentOutput, 0);
    }

    TopMotor.set(ControlMode.PercentOutput, Operator.getY());
    BottomMotor.set(ControlMode.PercentOutput, Operator.getY(GenericHID.Hand.kRight));
    //Shifting
    if (b_left_bumper && b_right_bumper) {
      return;
    } else if (b_left_bumper && state_shifter) {
      s_shifter.set(false);
    } else if (b_right_bumper && !state_shifter) {
      s_shifter.set(true);
    }
    //Threadmill(Intake)

    if (o_right_bumper == true){
      Threadmill.set(ControlMode.PercentOutput, 1);
    }
    else {
      Threadmill.set(ControlMode.PercentOutput, 0);
    }
    
  }


  public double acceleration(int mode) {
    if (mode == 0) {
      return m_stick.getY(GenericHID.Hand.kLeft);
    } else if (mode == 1) {
      double Left_trigger_value = (m_stick.getTriggerAxis(GenericHID.Hand.kLeft)+1)/2;
      double Right_trigger_value = (m_stick.getTriggerAxis(GenericHID.Hand.kRight)+1)/2;
      return Left_trigger_value-Right_trigger_value;
    }
    return 0;
  }
}
