package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.Servo;



//Created by Gus Caplan on 11/03/2015.

public class MainTeleOp extends OpMode {

    DcMotor right;
    DcMotor left;
    DcMotor back;

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
    public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
        */

        right = hardwareMap.dcMotor.get("right");
        left = hardwareMap.dcMotor.get("left");
        back = hardwareMap.dcMotor.get("back");

        //reverse left motors because they are facing opposite the right ones
        right.setDirection(DcMotor.Direction.REVERSE);

        arm.setDirection(DcMotor.Direction.REVERSE);


        right.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        left.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        back.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

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
        float backS = 0;
        float rightS = -gamepad1.right_stick_y;
        float leftS = -gamepad1.left_stick_y;


        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        rightS = (float) scaleInput(rightS);
        leftS = (float) scaleInput(leftS);

        rightS = Range.clip(rightS, -1, 1);
        leftS = Range.clip(leftS, -1, 1);

        right.setPower(rightS);
        left.setPower(leftS);

        backS = leftS - rightS;
        
        backS = Range.clip(backS, -1, 1); // if left stick was 1 and right stick was -1 then backS would be 2, which would make errors.

        back.setPower(backS);
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
