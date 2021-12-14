package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name = "EncoderAuto")
public class EncoderAuto extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    private static DcMotor frontLeft = null;
    private static DcMotor frontRight = null;
    private static DcMotor backLeft = null;
    private static DcMotor backRight = null;

    static final double     COUNTS_PER_MOTOR_REV = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED = 0.6;
    static final double     TURN_SPEED = 0.5;

    DcMotor[] wheels = new DcMotor[4];

    /**
     * Code to run
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "left_front");
        frontRight = hardwareMap.get(DcMotor.class, "right_front");
        backLeft = hardwareMap.get(DcMotor.class, "left_back");
        backRight = hardwareMap.get(DcMotor.class, "right_back");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        setSpeed(0);
        for (DcMotor wheel : wheels){
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        wheels[0].setTargetPosition(wheels[0].getTargetPosition());
        wheels[1].setTargetPosition(wheels[0].getTargetPosition());
        wheels[2].setTargetPosition(wheels[0].getTargetPosition());
        wheels[3].setTargetPosition(wheels[0].getTargetPosition());
        
        wheels[0] = frontLeft;
        wheels[1] = frontRight;
        wheels[2] = backLeft;
        wheels[3] = backRight;
        
        for (DcMotor wheel : wheels){
            wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());
        telemetry.update();

        waitForStart();
        driveForward(10, 1);
    /*    encoderDrive(DRIVE_SPEED,  -10,  10, 10, -10, 5.0);
        encoderDrive(DRIVE_SPEED,  8,  8, 8, 8, 5.0);
        encoderDrive(DRIVE_SPEED,  18,  -18, -18, 18, 5.0);
        encoderDrive(DRIVE_SPEED,  -6,  6, -6, 6, 5.0);
        encoderDrive(DRIVE_SPEED,  -5.5,  -5.5, -5.5, -5.5, 5.0);
        encoderDrive(DRIVE_SPEED,  12.8,  12.8, 12.8, 12.8, 5.0);
        encoderDrive(DRIVE_SPEED,  -6,  6, -6, 6, 5.0);
        encoderDrive(DRIVE_SPEED,  -9,  -9, -9, -9, 5.0);
//        encoderDrive(TURN_SPEED,   12, -12, 12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
//        encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
    */
    }
    
    public void driveForward(int dist, double speed){
        int target = (int) Math.round(COUNTS_PER_INCH * dist);
        for (int i = 0; i < 4; i++){
            wheels[i].setTargetPosition(wheels[i].getTargetPosition()+target);
            wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
       setSpeed(speed);
       
      while(frontLeft.isBusy()){
          sleep(1);
      }
      setSpeed(0);
    }
    public void setSpeed(double speed){
        for (int i = 0; i < 4; i++){
            wheels[i].setPower(speed);
        }
    }
    /*
    public void encoderDrive(double speed, double front_left, double front_right, double back_left, double back_right, double timeoutS) {
        int []newWheelTarget = new int[4];
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newWheelTarget[0] = wheels[0].getCurrentPosition() + (int)(front_left * COUNTS_PER_INCH);
            wheels[0].setTargetPosition(wheels[0].getCurrentPosition() newWheelTarget[0];
            for (int i = 0; i < 4; i++) {
                // Sets the target position for the motors
                wheels[i].setTargetPosition(newWheelTarget[i]);

                // Tells the motor to drive until they reach the target position
                wheels[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            // reset the timeout time and start motion.
            runtime.reset();

            frontLeft.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy()) {
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newWheelTarget[0],  newWheelTarget[1], newWheelTarget[2], newWheelTarget[3]);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backRight.getCurrentPosition(), backLeft.getCurrentPosition());
                telemetry.update();
            }

            for (DcMotor wheel : wheels){
                // Stops motors after motors have reached target position
                wheel.setPower(0);

                // Resets encoders
                wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            //  sleep(250);   // optional pause after each move
        }
    }
    */
}