package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands;

@Autonomous(name="Auto2")
public class Auto2 extends LinearOpMode {

    /* Declare OpMode members. */
    Commands commands = new Commands();

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            commands.moveForward(0.5,10, 15);
            commands.moveBackward(0.65, 15, 20);

            commands.rotateCounterClockwise(0.5, 10,20 );

            commands.duckCarouselCounterClockwise(0.5);
            commands.duckCarouselClockwise(0.5);
            sleep(30000);
        }
    }
}