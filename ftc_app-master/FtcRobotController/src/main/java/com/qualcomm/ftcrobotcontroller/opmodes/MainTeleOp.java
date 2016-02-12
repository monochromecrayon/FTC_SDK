package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.Servo;


/**
<<<<<<< HEAD
 * Created by Gus Caplan on 11/03/2015.
=======
 * Created by Gus Caplan on 11/3/2015.
>>>>>>> origin/master
 */

public class MainTeleOp extends OpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor winch;
    DcMotor arm;

<<<<<<< HEAD
    //Servo leftFlappy;
    //Servo rightFlappy;
    /**
     * Constructor
     */

=======
    Servo leftFlappy;
    Servo rightFlappy;
    /**
     * Constructor
     */
>>>>>>> origin/master
    public MainTeleOp() {

    }

<<<<<<< HEAD

=======
>>>>>>> origin/master
    /*
     * Code to run when the op mode is initialized goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
     */
    @Override
    public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
        */
<<<<<<< HEAD
       // leftFlappy = hardwareMap.servo.get("leftFlappy");
      //  rightFlappy = hardwareMap.servo.get("rightFlappy");

        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
=======
        leftFlappy = hardwareMap.servo.get("leftFlappy");
        rightFlappy = hardwareMap.servo.get("rightFlappy");

        motorFrontRight = hardwareMap.dcMotor.get("frontRight");

>>>>>>> origin/master
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");

        winch = hardwareMap.dcMotor.get("winch");
        arm = hardwareMap.dcMotor.get("arm");

        //reverse left motors because they are facing opposite the right ones
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
<<<<<<< HEAD
        arm.setDirection(DcMotor.Direction.REVERSE);
=======
>>>>>>> origin/master

        motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
<<<<<<< HEAD
        arm.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        winch.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

=======
>>>>>>> origin/master

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1 controls the driving.
		 * Gamepad 2 controls the arm.
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        float frontRight = 0;
        float frontLeft = 0;
        float backRight = 0;
        float backLeft = 0;
        float right = -gamepad1.right_stick_y;
        float left = -gamepad1.left_stick_y;
<<<<<<< HEAD





        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        //right = (float) scaleInput(right);
        //left = (float) scaleInput(left);

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
=======
        int rightTrigger = 0;
        int leftTrigger = 0;
        double rightPos = rightFlappy.getPosition();
        double leftPos = leftFlappy.getPosition();

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float) scaleInput(right);
        left = (float) scaleInput(left);
>>>>>>> origin/master

        frontRight = right;
        frontLeft = left;
        backRight = right;
        backLeft = left;

<<<<<<< HEAD

=======
>>>>>>> origin/master
        if(right == 0 && left == 0) {
            // separate wheel control
            frontRight = Math.round(gamepad1.right_trigger) - (gamepad1.right_bumper ? 1 : 0); // ternary operator "(CONDITION) ? IF_TRUE : IF_FALSE" "https://en.wikipedia.org/wiki/%3F:#Java"
            frontLeft = Math.round(gamepad1.left_trigger) - (gamepad1.left_bumper ? 1 : 0);
            backRight = (gamepad1.y ? 1 : 0) - (gamepad1.a ? 1 : 0);
            backLeft = (gamepad1.dpad_up ? 1 : 0) - (gamepad1.dpad_down ? 1 : 0);
        }

<<<<<<< HEAD

=======
>>>>>>> origin/master
        motorFrontRight.setPower(frontRight);
        motorFrontLeft.setPower(frontLeft);
        motorBackRight.setPower(backRight);
        motorBackLeft.setPower(backLeft);

        //////////////////////////
        float stick2ly = gamepad2.left_stick_y;
        float stick2ry = -gamepad2.right_stick_y;

<<<<<<< HEAD
        /*
        float sspeed = 0;
        if (gamepad2.a) {
            sspeed = (float) 0.3;
        } else {
            sspeed = 0;
        }

        if (gamepad2.left_trigger > 0.5) {
            //leftFlappy.setDirection(Servo.Direction.FORWARD);
            leftFlappy.setPosition(sspeed);
        }
        else if(gamepad2.left_bumper){
            //leftFlappy.setDirection(Servo.Direction.REVERSE);
            leftFlappy.setPosition(1-sspeed);}
=======



        if (gamepad2.left_trigger > 0.5) {
            leftFlappy.setDirection(Servo.Direction.FORWARD);
            leftFlappy.setPosition(0);
        }
        else if(gamepad2.left_bumper){
            leftFlappy.setDirection(Servo.Direction.REVERSE);
            leftFlappy.setPosition(0);}
>>>>>>> origin/master
        else{leftFlappy.setPosition(0.5);} // 0.5 is off, 0 is on


        if (gamepad2.right_trigger > 0.5) {
<<<<<<< HEAD
            //rightFlappy.setDirection(Servo.Direction.REVERSE);
            rightFlappy.setPosition(sspeed);
        }
        else if(gamepad2.right_bumper){
            //rightFlappy.setDirection(Servo.Direction.FORWARD);
            rightFlappy.setPosition(1-sspeed);}
        else{rightFlappy.setPosition(0.5);}
        */


        arm.setPower(stick2ly/3);
=======
            rightFlappy.setDirection(Servo.Direction.REVERSE);
            rightFlappy.setPosition(0);
        }
        else if(gamepad2.right_bumper){
            rightFlappy.setDirection(Servo.Direction.FORWARD);
            rightFlappy.setPosition(0);}
        else{rightFlappy.setPosition(0.5);}

        arm.setPower( stick2ly/5);
>>>>>>> origin/master
        winch.setPower(stick2ry);
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}
