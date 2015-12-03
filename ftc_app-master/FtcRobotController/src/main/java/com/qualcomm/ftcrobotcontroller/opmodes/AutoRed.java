package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Gus Caplan on 11/3/2015.
 */

public class AutoRed extends OpMode{

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor arm;

    GyroSensor sensorGyro;

    final static double CIRCUMFERENCE = Math.PI * Constants.WHEEL_DIAMETER;
    final static double ROTATIONS = Constants.DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = Constants.ENCODER_CPR * ROTATIONS * Constants.GEAR_RATIO;

    int xVal, yVal, zVal = 0;
    int heading = 0;

    @Override
    public void init(){

        motorFrontRight = hardwareMap.dcMotor.get("motor_1");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_2");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        arm = hardwareMap.dcMotor.get("arm");

        sensorGyro  = hardwareMap.gyroSensor.get("accel");

        sensorGyro.calibrate();

        while(sensorGyro.isCalibrating()){
            Thread.sleep(50);
        }
    }

    @Override
    public void start(){
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

        xVal = sensorGyro.rawX();
        yVal = sensorGyro.rawY();
        zVal = sensorGyro.rawZ();
    }

    public void loop(){
        telemetry.addData("Target", COUNTS);
        telemetry.addData("FR POS", motorFrontRight.getCurrentPosition());
        telemetry.addData("FL POS", motorFrontLeft.getCurrentPosition());
        telemetry.addData("BR POS", motorBackRight.getCurrentPosition());
        telemetry.addData("BL POS", motorBackLeft.getCurrentPosition());
        telemetry.addData("x", String.format("%03d", xVal));
        telemetry.addData("y", String.format("%03d", yVal));
        telemetry.addData("z", String.format("%03d", zVal));
        telemetry.addData("h", String.format("%03d", heading));
    }
}
