package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class HardwareMapping
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public DcMotor  carouselMotor = null;

    public BNO055IMU imu = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareMapping(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor = setupMotor("leftMotor", DcMotor.Direction.REVERSE, 0, true,true);
        rightMotor = setupMotor("rightMotor", DcMotor.Direction.FORWARD, 0, true,true);
        carouselMotor = setupMotor("carouselMotor",  0);
//        intakeServo1 = setupServo("intakeServo1", 0);
//        clawServo = setupServo("clawServo",  0);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hwMap.get(BNO055IMU.class,"imu");
        imu.initialize(parameters);
    }

    /* Init Motor, set direction, initial power and encoder runmode (if applicable)
     * @return the configured DcMotor or null if the motor is not found
     */
    private DcMotor setupMotor(String name, DcMotorSimple.Direction direction, double initialPower, boolean useEncoder, boolean brakeMode){
        try {
            DcMotor motor = hwMap.get(DcMotor.class, name);
            motor.setDirection(direction);
            motor.setPower(initialPower);

            if (useEncoder){
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            if(brakeMode){
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }else{
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            return motor;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init CRServo and set initial power
     * @return the configured CRServo or null if the servo is not found
     */
    private CRServo setupCRServo(String name, double initialPower){
        try {
            CRServo servo = hwMap.get(CRServo.class, name);
            servo.setPower(initialPower);
            return servo;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init Servo and set initial position
     * @return the configured Servo or null if the servo is not found
     */
    private Servo setupServo(String name, double initialPosition){
        try {
            Servo servo = hwMap.get(Servo.class, name);
            servo.setPosition(initialPosition);
            return servo;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init WebcamName
     * @return the configured WebcamName or null if the webcam is not found
     */
    private WebcamName setupWebcam(String name){
        try {
            WebcamName webcamName = hwMap.get(WebcamName.class, name);
            return webcamName;
        }
        catch(Exception e) {
            return null;
        }
    }
}
