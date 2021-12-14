package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Test")
public class Test extends LinearOpMode {
    
    private DcMotor rightBack;
    private DcMotor leftBack;
    private DcMotor leftCarousel;

    @Override
    public void runOpMode() {
        double vertical;

        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftCarousel = hardwareMap.get(DcMotor.class, "leftCarousel");

        waitForStart();

        if (opModeIsActive()) {
            // vertical = -gamepad1.left_stick_y;

            while (opModeIsActive()) {
                rightBack.setPower(1);
                leftBack.setPower(1);
                leftCarousel.setPower(1);
                

                telemetry.update();
            }
        }
    }
};