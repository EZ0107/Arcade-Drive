/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This is a demo program showing the use of the DifferentialDrive class.
 * Runs the motors with arcade steering.
 */
public class Robot extends TimedRobot {
  private final WPI_TalonFX FrontLeftMotor = new WPI_TalonFX(0);
  private final WPI_TalonFX FrontRightMotor = new WPI_TalonFX(1);
  private final WPI_TalonFX BackLeftMotor = new WPI_TalonFX(2);
  private final WPI_TalonFX BackRightMotor = new WPI_TalonFX(3);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(FrontLeftMotor, FrontRightMotor);
  private final XboxController Driver = new XboxController(0);
  private final Solenoid Shifter = new Solenoid(0);
  //Operator
  private final XboxController Operator = new XboxController(1);
  private final VictorSP Threadmill = new VictorSP(0);
  private final VictorSP Top_Flywheels = new VictorSP(1);
  private final VictorSP Bottom_Flywheels = new VictorSP(2);


  @Override
  public void teleopInit() {
    FrontLeftMotor.follow(BackLeftMotor);
    FrontRightMotor.follow(BackRightMotor);
  }
  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(Driver.getY(), Driver.getX(GenericHID.Hand.kRight));
    //Variables for shifting
    boolean Value_Shifter = Shifter.get();
    boolean Driver_RB = Driver.getBumper(GenericHID.Hand.kRight);
    boolean Driver_LB = Driver.getBumper(GenericHID.Hand.kLeft);

    if (Driver_LB && Driver_RB)
    {
      return;
    }
    else if(Driver_LB && Value_Shifter){
      Shifter.set(false);
    }
    else if(Driver_RB && !Value_Shifter){
      Shifter.set(true);
    }

    //Variable and Conditionals for the Intake
    boolean Operator_RB = Operator.getBumper(GenericHID.Hand.kRight);

    if (Operator_RB == true){
      Threadmill.set(1);
    }
    else if (Operator_RB == false){
      Threadmill.set(0);
    }

    //Flywheels
    Top_Flywheels.set(Operator.getY(GenericHID.Hand.kLeft));
    Bottom_Flywheels.set(Operator.getY(GenericHID.Hand.kRight));
    if (Bottom_Flywheels.get()-Top_Flywheels.get() == 0.1){
      Top_Flywheels.set(Bottom_Flywheels.get());
    }
    if(Top_Flywheels.get()-Bottom_Flywheels.get() == 0.1){
      Top_Flywheels.set(Bottom_Flywheels.get());
    }
  }
}
