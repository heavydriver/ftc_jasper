package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor leftMotor = hardwareMap.dcMotor.get("leftMotor");
        DcMotor rightMotor = hardwareMap.dcMotor.get("rightMotor");
        CRServo carouselServo = hardwareMap.crservo.get("carousel_servo");
        DcMotor arm = hardwareMap.dcMotor.get("arm_motor");
        DcMotor wrist = hardwareMap.dcMotor.get("wrist_motor");
        Servo intake = hardwareMap.servo.get("intake_servo");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y)+ Math.abs(rx), 1);
            double leftMotorPower = (y + rx) / denominator;
            double rightMotorPower = (y - rx) / denominator;

            leftMotor.setPower(leftMotorPower);
            rightMotor.setPower(rightMotorPower);

            // carouselServo.setPower(gamepad1.right_stick_y);
            if (gamepad1.b){
                carouselServo.setPower(-1);
            } else if (gamepad1.y){
                carouselServo.setPower(0);
            } else if (gamepad1.x){
                carouselServo.setPower(1);
            }

            if (gamepad2.x){
                carouselServo.setPower(1);
            } else if (gamepad1.b) {
                carouselServo.setPower(0.5);
            }
//            } else if (gamepad1.x){
//                carouselServo.setPower(-1);
//            }

            arm.setPower(-gamepad2.left_stick_y);
            wrist.setPower(-gamepad2.right_stick_y);



//            if (gamepad1.left_trigger==1) {
//                arm.setPower(1);
//            } else if (gamepad1.left_trigger==1) {
//                arm.setPower(-1);
//            }
        }
    }
}