package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.lang.Thread;

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

                
                leftCarousel.setPower(1);
                moveForward(rightBack, leftBack, 1000);
                moveBackward(rightBack, leftBack, 1000);
                

                telemetry.update();
            }
        }
    }

    // time in miliseconds
    public void moveForward(DcMotor rightBack, DcMotor leftBack, int time) {
            leftBack.setDirection(DcMotor.Direction.REVERSE);

            rightBack.setPower(1);
            leftBack.setPower(1);
            Thread.sleep(time);

            rightBack.setPower(0);
            leftBack.setPower(0);

            leftBack.setDirection(DcMotor.Direction.FORWARD);
    }

    public void moveBackward(DcMotor rightBack, DcMotor leftBack, int time) {
            rightBack.setDirection(DcMotor.Direction.REVERSE);

            rightBack.setPower(1);
            leftBack.setPower(1);
            Thread.sleep(time);

            rightBack.setPower(0);
            leftBack.setPower(0);

            rightBack.setDirection(DcMotor.Direction.FORWARD);
    }
};