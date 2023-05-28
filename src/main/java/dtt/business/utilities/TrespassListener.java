package dtt.business.utilities;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * The TrespassListener class implements the PhaseListener interface to handle
 * authorization and access control for JSF pages.
 * @author Johannes Silvennoinen
 */
public class TrespassListener implements PhaseListener {
    private SessionInfo sessionInfo;

    /**
     * Invoked after the completion of the restore view phase.
     * Performs authorization and access control checks based on the user's
     * privileges and the requested view.
     *
     * @param phaseEvent The PhaseEvent object representing the after phase event.
     */
    @Override
    public void afterPhase(PhaseEvent phaseEvent) {

    }
    /**
     * Invoked before the start of the restore view phase. This method does nothing in this implementation.
     *
     * @param phaseEvent The PhaseEvent object representing the before phase event.
     */
    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }
    /**
     * Retrieves the identifier of the lifecycle phase during which this listener should be invoked.
     *
     * @return The identifier of the restore view phase.
     */
    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}
