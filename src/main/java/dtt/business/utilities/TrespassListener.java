package dtt.business.utilities;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * Checks requests on user authentication.
 * @author Johannes Silvennoinen
 */
public class TrespassListener implements PhaseListener {
    private SessionInfo sessionInfo;
    @Override
    public void afterPhase(PhaseEvent phaseEvent) {

    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }

    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}
