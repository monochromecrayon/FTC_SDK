package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.ServoController;

import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import android.util.Log;


/**
 * Created by Gus Caplan on 11/3/2015.
 */

public class FullMontyBlue extends LinearOpMode{

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor winch;
    DcMotor arm;
    GyroSensor sensorGyro; //OLD VERSION
    OpticalDistanceSensor ODS;
    //ColorSensor sensorColorLeft;
    //ColorSensor sensorColorRight;
    //ModernRoboticsI2cGyro sensorGyro;  //MODERN ROBOTICS VERSION

    Servo leftFlappy; // SERVOS GET REKT
    Servo rightFlappy;
    Servo ODSS;



    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1.5;
    final static int WHEEL_DIAMETER = 6;
    final static int DISTANCE = 72;
    public int count = 0;

    final static double lightTolerance = 0.3;

    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    final static double ROTATIONS = 72 / CIRCUMFERENCE;

    //int xVal, yVal, zVal = 0;
    //int heading = 0;




    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        arm = hardwareMap.dcMotor.get("arm");
        //ModernRoboticsI2cGyro sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro = hardwareMap.gyroSensor.get("gyro"); // OLD GYRO
        //sensorColorLeft = hardwareMap.colorSensor.get("colorLeft");
        // sensorColorRight = hardwareMap.colorSensor.get("colorRight");
        winch = hardwareMap.dcMotor.get("winch");
        hardwareMap.logDevices();
        // bEnabled represents the state of the LED.
        ODS= hardwareMap.opticalDistanceSensor.get("ODS");
        ODSS = hardwareMap.servo.get("ODSS");
        leftFlappy = hardwareMap.servo.get("leftFlappy");
        rightFlappy = hardwareMap.servo.get("rightFlappy");
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        //sensorColorRight.enableLed(false);
        // sensorColorLeft.enableLed(false);

        waitOneFullHardwareCycle();

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        motorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


        sensorGyro.calibrate();
        double ODSCal = ODS.getLightDetected();

        double turnPower = 0.8;
        ODSS.setPosition(0);
        leftFlappy.setPosition(0.5);
        rightFlappy.setPosition(0.5);
        waitForStart();
        leftFlappy.setPosition(0.5);
        rightFlappy.setPosition(0.5);

        while (sensorGyro.isCalibrating()) {
            Thread.sleep(50);}

        trackHeading(0, 60, 0.5);

        arm.setPower(0.5);
        sleep(350);
        arm.setPower(0);

        //trackHeading(0, 30, 0.6);
        ODSS.setPosition(0.7);
        trackHeadingLightStop(0, 40, 0.4, ODSCal, lightTolerance);
        allStop();
        sleep(500);


        ODSS.setPosition(0);

        turn(40, turnPower);
        sleep(500);
        trackHeading(45,10,0.6);
        forward(500,1);
        sleep(500);


        arm.setPower(0.5);
        sleep(900);
        arm.setPower(0);
        sleep(200);
        arm.setPower(-.5);
        sleep(400);
        arm.setPower(0);

        sleep(2000);
        encoderBackwards(20, 0.6);
        sleep(500);

        turn(170, turnPower);
        trackHeading(180, 24, 0.6);
        sleep(500);

        turn(95, turnPower);
        sleep(500);
        trackHeading(90, 100, 1);

        allStop();



        //SENSOR TEST
        /*
        while (opModeIsActive()) {

            telemetry.addData("rawX", sensorGyro.rawX());
            //telemetry.addData("Heading ", sensorGyro.getHeading());
            //telemetry.addData("Red  ", sensorColorLeft.red());
            //telemetry.addData("Green", sensorColorLeft.green());
            //telemetry.addData("Blue ", sensorColorLeft.blue());
            //telemetry.addData("arm_count", arm.getCurrentPosition());
            //waitOneFullHardwareCycle();

            Thread.sleep(100);
        }
    */

    }



    /////////////////////
    //MOVEMENT COMMANDS//
    /////////////////////

    public void trackHeading(int desired_heading, int distance, double power)throws InterruptedException {
        int millis = 5000;
        double _power = power;
        int ENCODER_CPR = 1120;
        double GEAR_RATIO = 1.5;
        int WHEEL_DIAMETER = 6;
        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        int blcount = motorBackLeft.getCurrentPosition();
        int brcount = motorBackRight.getCurrentPosition();
        int frcount = motorFrontRight.getCurrentPosition();
        int flcount = motorFrontLeft.getCurrentPosition();

        double COUNTSBL = blcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSBR = brcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSFR = frcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSFL = flcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        resetStartTime();
        int my_heading = sensorGyro.getHeading();
        while ((flcount + frcount)/2 < ((COUNTSFL + COUNTSFR)/2) && getRuntime() < millis ) {
            blcount = motorBackLeft.getCurrentPosition();
            brcount = motorBackRight.getCurrentPosition();
            frcount = motorFrontRight.getCurrentPosition();
            flcount = motorFrontLeft.getCurrentPosition();
            my_heading = sensorGyro.getHeading();
            int delta = desired_heading - my_heading;  //Left Turn Example 340 - 20 ... delta=320  Right Turn Example 20 - 340... delta = -320
            if (Math.abs(delta) > 180) {
                if (desired_heading >= 270) {
                    delta = -(360 - desired_heading + my_heading);  //Left Turn example delta = -(360-340+20) = -40
                }
                if (desired_heading <= 90) {
                    delta = desired_heading + (360 - my_heading);
                }  //Right Turn exmaple delta = 20 + (360-340) = 40
            }
            if (blcount < COUNTSBL + 1000 && brcount < COUNTSBR + 1000) {
                power = .5;
            }
            if (delta > 0) {
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorFrontRight.setPower(power / delta);
                motorBackRight.setPower(power / delta);
            }
            else if (delta < 0) {
                motorBackLeft.setPower(-power / delta);
                motorFrontLeft.setPower(-power / delta);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }
            else {
                motorFrontRight.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorBackLeft.setPower(power);

            }
            sleep(20);


        }
        for(double i = power; i > 0; i = i - _power/10){
            motorFrontRight.setPower(power * i);
            motorFrontLeft.setPower(power * i);
            motorBackRight.setPower(power * i);
            motorBackLeft.setPower(power * i);
            sleep(10);
        }

        allStop();
    }

    public void trackHeadingLightStop(int desired_heading, int distance, double power, double ODSCal, double lightTolerance)throws InterruptedException {
        int millis = 10000;
        double _power = power;
        int ENCODER_CPR = 1120;
        double GEAR_RATIO = 1.5;
        int WHEEL_DIAMETER = 6;
        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        int blcount = motorBackLeft.getCurrentPosition();
        int brcount = motorBackRight.getCurrentPosition();
        int frcount = motorFrontRight.getCurrentPosition();
        int flcount = motorFrontLeft.getCurrentPosition();

        double COUNTSBL = blcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSBR = brcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSFR = frcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        double COUNTSFL = flcount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        resetStartTime();
        int my_heading = sensorGyro.getHeading();
        while ((flcount + frcount)/2 < ((COUNTSFL + COUNTSFR)/2) && getRuntime() < millis && (ODS.getLightDetected() < ODSCal + lightTolerance)) {
            blcount = motorBackLeft.getCurrentPosition();
            brcount = motorBackRight.getCurrentPosition();
            frcount = motorFrontRight.getCurrentPosition();
            flcount = motorFrontLeft.getCurrentPosition();
            my_heading = sensorGyro.getHeading();
            int delta = desired_heading - my_heading;  //Left Turn Example 340 - 20 ... delta=320  Right Turn Example 20 - 340... delta = -320
            if (Math.abs(delta) > 180) {
                if (desired_heading >= 270) {
                    delta = -(360 - desired_heading + my_heading);  //Left Turn example delta = -(360-340+20) = -40
                }
                if (desired_heading <= 90) {
                    delta = desired_heading + (360 - my_heading);
                }  //Right Turn exmaple delta = 20 + (360-340) = 40
            }
            if (blcount < COUNTSBL + 1000 && brcount < COUNTSBR + 1000) {
                power = .5;
            }
            if (delta > 0) {
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorFrontRight.setPower(power / delta);
                motorBackRight.setPower(power / delta);
            }
            else if (delta < 0) {
                motorBackLeft.setPower(-power / delta);
                motorFrontLeft.setPower(-power / delta);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);

            }
            else {
                motorFrontRight.setPower(power);
                motorFrontLeft.setPower(power);
                motorBackRight.setPower(power);
                motorBackLeft.setPower(power);

            }
            sleep(20);


        }


        allStop();
    }

    public int encoderForward(int distance, double power)throws InterruptedException {

        ////////MOVE FORWARD
        int startCount = motorBackLeft.getCurrentPosition();
        int ENCODER_CPR = 1120;
        double GEAR_RATIO = 1.5;
        int WHEEL_DIAMETER = 6;
        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = startCount + ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        count = motorBackLeft.getCurrentPosition();
        sleep(100);
        while (count < COUNTS) {
            motorFrontRight.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorBackLeft.setPower(power);
            count = motorBackLeft.getCurrentPosition();
            sleep(20);
        }

        allStop();
        sleep(100);
        count = motorBackLeft.getCurrentPosition();

        return count;

    }public int encoderBackwards(int distance, double power)throws InterruptedException {

        ////////MOVE FORWARD
        int startCount = motorBackLeft.getCurrentPosition();
        int ENCODER_CPR = 1120;
        double GEAR_RATIO = 1.5;
        int WHEEL_DIAMETER = 6;
        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = startCount - ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        count = motorBackLeft.getCurrentPosition();
        sleep(100);
        while (count > COUNTS) {
            motorFrontRight.setPower(-power);
            motorFrontLeft.setPower(-power);
            motorBackRight.setPower(-power);
            motorBackLeft.setPower(-power);
            count = motorBackLeft.getCurrentPosition();
            sleep(20);
        }

        allStop();
        sleep(100);
        count = motorBackLeft.getCurrentPosition();

        return count;

    }

    public void arm(int finalCount, double power)throws InterruptedException {
        int startcount = arm.getCurrentPosition();
        int counts = startcount;
        if (power > 0) {
            while (counts < (finalCount + startcount)) {
                counts = arm.getCurrentPosition();
                arm.setPower(power);
                sleep(10);
            }
        }
        if (power < 0) {
            while (counts > (finalCount - startcount)) {
                counts = arm.getCurrentPosition();
                arm.setPower(power);
                sleep(10);

            }

            sleep(100);
        }
    }
    public void turnLeft(int heading, double power)throws InterruptedException {
        // ROTATE LEFT to heading degrees
        sleep(100);
        double _power = power;
        motorFrontRight.setPower(1);
        motorFrontLeft.setPower(-1);
        motorBackRight.setPower(1);
        motorBackLeft.setPower(-1);
        sleep(50);
        int my_heading = sensorGyro.getHeading();
        while (my_heading > heading) {
            motorFrontRight.setPower(power);
            motorFrontLeft.setPower(-power);
            motorBackRight.setPower(power);
            motorBackLeft.setPower(-power);
            if (Math.abs(my_heading-heading)< 20){power = _power * 0.5; }

            my_heading = sensorGyro.getHeading();
            sleep(10);

        }
        allStop();
        sleep(100);


    }
    public void turn(int heading, double power)throws InterruptedException {
        // ROTATE LEFT to heading degrees
        int my_heading = sensorGyro.getHeading();
        double _power = power;
        my_heading = sensorGyro.getHeading();

        int delta = heading - my_heading;  //Left Turn Example 340 - 20 ... delta=320  Right Turn Example 20 - 340... delta = -320
        if (Math.abs(delta) > 180) {
            if (heading >= 270) {
                delta = -(360 - heading + my_heading);  //Left Turn example delta = -(360-340+20) = -40
            }
            if (heading <= 90) {
                delta = heading + (360 - my_heading);
            }  //Right Turn exmaple delta = 20 + (360-340) = 40
        }


        while (Math.abs(delta) > 5) {
            my_heading = sensorGyro.getHeading();
            delta = heading - my_heading;  //Left Turn Example 340 - 20 ... delta=320  Right Turn Example 20 - 340... delta = -320
            if (Math.abs(delta) > 180) {
                if (heading >= 270) {
                    delta = -(360 - heading + my_heading);  //Left Turn example delta = -(360-340+20) = -40
                }
                if (heading <= 90) {
                    delta = heading + (360 - my_heading);
                }  //Right Turn exmaple delta = 20 + (360-340) = 40
            }


            if (Math.abs(my_heading-heading)< 15){power = _power * 0.5; }

            if (delta > 0) {
                motorBackLeft.setPower(power);
                motorFrontLeft.setPower(power);
                motorFrontRight.setPower(-power);
                motorBackRight.setPower(-power);
            }
            else if (delta < 0) {
                motorBackLeft.setPower(-power);
                motorFrontLeft.setPower(-power);
                motorBackRight.setPower(power);
                motorFrontRight.setPower(power);
            }
            sleep(10);

        }
        allStop();


    }

    public void allStop()throws InterruptedException {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        sleep(100);
    }

    public void forward(int time, double power) throws InterruptedException{


            motorFrontRight.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorBackLeft.setPower(power);
            sleep(time);
        allStop();
        }

    }



