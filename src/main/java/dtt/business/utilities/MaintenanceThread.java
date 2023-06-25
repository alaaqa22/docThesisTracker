package dtt.business.utilities;

import dtt.global.utilities.ConfigReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a maintenance thread for sending reminder emails.
 * This thread periodically sends reminder emails to users based on certain criteria.
 * @author Johannes Silvennoinen
 */
@ApplicationScoped
public class MaintenanceThread extends TimerTask {
    private Timer timer;
    @Inject
    TokenManager tokenManager;
    /**
     * Executes the maintenance tasks.
     * This method sends reminder emails to users based on certain criteria.
     */
    @Override
    public void run() {
        tokenManager.clearExpiredTokens();
    }
    /**
     * Starts the maintenance thread.
     * This method creates a new Timer instance and schedules the maintenance task to run at specified intervals.
     */
    public void startMaintenance() {
        timer = new Timer();
        //Once a day
        timer.schedule(this, 0, 24 * 60 * 60 * 1000);
    }

    /**
     * Stops the maintenance thread.
     * This method cancels the timer and stops the execution of the maintenance task.
     */
    public void stopMaintenance() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
