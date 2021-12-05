package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Commands extends HardwareMapping{

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    public void moveForward(double power, double distance, double timeout) {
        this.encoderDrive(power, distance, distance, timeout);
    }

    public void moveBackward(double power, double distance, double timeout) {
        this.encoderDrive(power, -distance, -distance, timeout);
    }

    public void rotateCounterClockwise(double power, double distance, double timeout) {
        this.encoderDrive(power, -distance, distance, timeout);
    }

    public void rotateClockwise(double power, double distance, double timeout) {
        this.encoderDrive(power, distance, -distance, timeout);
    }

    /**
     * Init of the robot always zero's out the heading
     * This will turn the robot Clockwise direction until
     * the gyro is at this heading within HEADING_ERROR
     * @param power [-1..1]
     * @param heading [0..360] heading based on init of robot
     * @param timeout seconds until abort for Autonomous
     */
    public void rotateClockwiseGyro(double power,double heading,double timeout){
        this.gyroTurn(power,heading,timeout);
    }

    /**
     * Init of the robot always zero's out the heading
     * This will turn the robot Counter Clockwise direction until
     * the gyro is at this heading within HEADING_ERROR
     * @param power [-1..1]
     * @param heading [0..360] heading based on init of robot
     * @param timeout seconds until abort for Autonomous
     */
    public void rotateCounterClockwiseGyro(double power,double heading,double timeout){
        this.gyroTurn(-power,heading,timeout);
    }

    public void duckCarouselClockwise(double power) {
        carouselServo.setPower(power);
    }

    public void duckCarouselCounterClockwise(double power) {
        carouselServo.setPower(-power);
    }

    private void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = leftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newRightTarget = rightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        leftMotor.setTargetPosition(newLeftTarget);
        rightMotor.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        leftMotor.setPower(Math.abs(speed));
        rightMotor.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while ((runtime.seconds() < timeoutS) && (leftMotor.isBusy() && rightMotor.isBusy())) {

        }

        // Stop all motion;
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    private void gyroTurn(double speed, double heading, double timeout){
        runtime.reset();

        while(Math.abs(getError(heading)) >= 5 && (runtime.seconds() < timeout)){
            leftMotor.setPower(speed);
            rightMotor.setPower(-speed);
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    public double getError(double targetAngle) {
        Orientation angles;
        double robotError;

        // calculate error in -179 to +180 range  (
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robotError = targetAngle - angles.firstAngle;
        return robotError;
    }
}