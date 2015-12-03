package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Gus Caplan on 11/3/2015.
 */

public class MainTeleOp extends OpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor winch;
    DcMotor arm;

    /**
     * Constructor
     */
    public MainTeleOp() {

    }

    /*
     * Code to run when the op mode is initialized goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
     */
    @Override
    public void init_loop() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
        */

        motorFrontRight = hardwareMap.dcMotor.get("motor_1");

        motorFrontLeft = hardwareMap.dcMotor.get("motor_2");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        winch = hardwareMap.dcMotor.get("winch");
        arm = hardwareMap.dcMotor.get("claw");

        //reverse left motors because they are facing opposite the right ones
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

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

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float) scaleInput(right);
        left = (float) scaleInput(left);

        frontRight = right;
        frontLeft = left;
        backRight = right;
        backLeft = left;

        if(right == 0 && left == 0) {
            // separate wheel control
            frontRight = Math.round(gamepad1.right_trigger) - (gamepad1.right_bumper ? 1 : 0); // ternary operator "(CONDITION) ? IF_TRUE : IF_FALSE" "https://en.wikipedia.org/wiki/%3F:#Java"
            frontLeft = Math.round(gamepad1.left_trigger) - (gamepad1.left_bumper ? 1 : 0);
            backRight = (gamepad1.y ? 1 : 0) - (gamepad1.a ? 1 : 0);
            backLeft = (gamepad1.dpad_up ? 1 : 0) - (gamepad1.dpad_down ? 1 : 0);
        }

        motorFrontRight.setPower(frontRight);
        motorFrontLeft.setPower(frontLeft);
        motorBackRight.setPower(backRight);
        motorBackLeft.setPower(backLeft);

        //////////////////////////
        float stick2ly = gamepad2.left_stick_y;
        float stick2ry = -gamepad2.right_stick_y;

        arm.setPower(stick2ly/5);
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
