package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Gripper")
public class Gripper extends LinearOpMode {
    
    private DcMotor gripperMotor;

    @Override
    public void runOpMode() {
        double vertical;

        gripperMotor = hardwareMap.get(DcMotor.class, "right_front");

        waitForStart();

        if (opModeIsActive()) {
            vertical = -gamepad2.left_stick_y;
            
            while (opModeIsActive()) {
                gripperMotor.setPower(vertical);

                telemetry.update();
            }
        }
    }
}