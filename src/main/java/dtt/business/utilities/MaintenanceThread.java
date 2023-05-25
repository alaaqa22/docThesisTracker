package dtt.business.utilities;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a maintenance thread for sending reminder emails.
 * This thread periodically sends reminder emails to users based on certain criteria.
 * @author Johannes Silvennoinen
 */
public class MaintenanceThread extends TimerTask {
    private Timer timer;

    /**
     * Executes the maintenance tasks.
     * This method sends reminder emails to users based on certain criteria.
     */
    @Override
    public void run() {
        //perform maintenance tasks
    }
    /**
     * Starts the maintenance thread.
     * This method creates a new Timer instance and schedules the maintenance task to run at specified intervals.
     */
    public void startMaintenance() {
        //Schedule the maintenance task to run at specified intervals
        //timer.schedule(this, 0, 5000) //Adjust interval as needed
    }

    /**
     * Stops the maintenance thread.
     * This method cancels the timer and stops the execution of the maintenance task.
     */
    public void stopMaintenance() {
        //if (timer != null)
        //timer.cancel();
        //timer = null
    }
}
