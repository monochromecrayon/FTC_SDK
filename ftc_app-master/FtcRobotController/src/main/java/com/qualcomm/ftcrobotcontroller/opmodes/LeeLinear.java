package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import android.util.Log;

/**
 * Created by Gus Caplan on 11/3/2015.
 */

public class LeeLinear extends LinearOpMode{
    //private static final String TAG = "ROBOT";

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor arm;

    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1.5;
    final static int WHEEL_DIAMETER = 6;
    final static int DISTANCE = 72;

    GyroSensor sensorGyro;

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = 72 / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    //int xVal, yVal, zVal = 0;
    //int heading = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("motor_1");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_2");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        arm = hardwareMap.dcMotor.get("claw");

        // sensorGyro = hardwareMap.gyroSensor.get("accel");

        // sensorGyro.calibrate();

       /* while(sensorGyro.isCalibrating()){
            return;
        }
        */
        //currentMessage = "initialized";
        waitForStart();

        motorFrontRight.setTargetPosition((int) COUNTS);
        motorFrontLeft.setTargetPosition((int) COUNTS);
        motorBackRight.setTargetPosition((int) COUNTS);
        motorBackLeft.setTargetPosition((int) COUNTS);

        motorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(0.5);
        motorFrontLeft.setPower(0.5);
        motorBackRight.setPower(0.5);
        motorBackLeft.setPower(0.5);
        //while (motorBackLeft.isBusy()) {}

        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

        motorFrontRight.setTargetPosition((int) 0);
        motorFrontLeft.setTargetPosition((int) 0);
        motorBackRight.setTargetPosition((int) 0);
        motorBackLeft.setTargetPosition((int) 0);

        motorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(-0.5);
        motorFrontLeft.setPower(-0.5);
        motorBackRight.setPower(-0.5);
        motorBackLeft.setPower(-0.5);

        //while (motorBackLeft.isBusy()) {}





    }


}
