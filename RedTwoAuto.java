package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by nihal on 1/17/18.
 */


@Autonomous(name="Red 1", group = "Tourney Codes")

public class RedTwoAuto extends LinearOpMode {

    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor armMotor;
    //left
    private Servo handServo1;
    //right
    private Servo handServo2;
    private Servo colorServo;

    //position values
    private final static int revTicks = 1120;
    private final static int gearValue = 2;
    private final double wheelDiamerter = 6;
    private final double pi = Math.PI;
    private final double wheelCircumference = wheelDiamerter * pi;
    private final int ELEVATOR_TOP_POSITION = -2900;
    private final int ELEVATOR_BOTTOM_POSITION = 0;

    //The value below shows how many ticks there are per inch
    private double ticksPerInch = revTicks/wheelCircumference;


    public void runOpMode() {

        motorLeft = hardwareMap.dcMotor.get("motorTest2");
        motorRight = hardwareMap.dcMotor.get("motorTest");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        colorServo = hardwareMap.servo.get("armServo");
        handServo1 = hardwareMap.servo.get("servo1");
        handServo2 = hardwareMap.servo.get("servo2");
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorRight.setDirection(DcMotor.Direction.REVERSE);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();


        closeElevator();
        sleep(500);
        liftUp();

        drive(.3,40);
        sleep(1500);

        turnLeft(3);
        drive(.2,5);
        sleep(1500);

        openElevator();
        sleep(500);

        drive(.2,-2);
        stop();



    }

    public void drive(double power,double inch){

        motorRight.setTargetPosition((int)(motorRight.getCurrentPosition() + inch * ticksPerInch));
        motorLeft.setTargetPosition((int)(motorLeft.getCurrentPosition() + inch * ticksPerInch));

        motorLeft.setPower(power);
        motorRight.setPower(power);

        while (motorRight.isBusy() && motorLeft.isBusy());
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }

    public void Turn(int inch){
        //for 180 degrees inch = 23

        motorRight.setTargetPosition((int)(motorRight.getCurrentPosition() + inch * ticksPerInch));
        motorLeft.setTargetPosition((int)(motorLeft.getCurrentPosition() - inch * ticksPerInch));
        motorLeft.setPower(0.2);
        motorRight.setPower(0.2);
        while (motorRight.isBusy() && motorLeft.isBusy());
    }

    public void resetEncoders(){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void turnLeft(double inch){
        motorRight.setTargetPosition((int)(motorRight.getCurrentPosition() + inch * ticksPerInch));

        motorRight.setPower(.1);
        while (motorRight.isBusy() && motorLeft.isBusy());
    }

    public void turnRight(double inch){
        motorLeft.setTargetPosition((int)(motorLeft.getCurrentPosition() + inch * ticksPerInch));

        motorLeft.setPower(.1);
    }

    public void closeElevator(){
        handServo1.setPosition(0);
        handServo2.setPosition(1);
    }

    public void openElevator(){
        handServo1.setPosition(.5);
        handServo2.setPosition(.2);
    }

    public void liftUp(){
        armMotor.setTargetPosition(ELEVATOR_TOP_POSITION/3);
        armMotor.setPower(.5);
        while(armMotor.isBusy());
    }
    public void setDown(){
        armMotor.setTargetPosition(ELEVATOR_BOTTOM_POSITION);
        armMotor.setPower(.5);
        while(armMotor.isBusy());
    }
}