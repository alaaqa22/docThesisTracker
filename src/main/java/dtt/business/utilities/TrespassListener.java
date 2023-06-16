package dtt.business.utilities;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The TrespassListener class implements the PhaseListener interface to handle
 * authorization and access control for JSF pages.
 * @author Johannes Silvennoinen
 */
public class TrespassListener implements PhaseListener {
    private static final Logger LOGGER = LogManager.getLogger(TrespassListener.class);

    /**
     * Invoked after the completion of the restore view phase.
     * Performs authorization and access control checks based on the user's
     * privileges and the requested view.
     *
     * @param phaseEvent The PhaseEvent object representing the after phase event.
     */
    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        LOGGER.info("afterPhase started!");

        final FacesContext fctx = phaseEvent.getFacesContext();
        final ExternalContext ctx = fctx.getExternalContext();
        final SessionInfo sessionInfo = CDI.current().select(SessionInfo.class).get();

        final UIViewRoot viewRoot = fctx.getViewRoot();
        if (viewRoot != null) {
            final String url = viewRoot.getViewId();
            final boolean isAllowed = checkAccessControl(url, sessionInfo);

            if (!isAllowed) {
                redirectTo404Page(ctx);
                fctx.responseComplete();
            }
        }
    }
    private boolean checkAccessControl(String url, SessionInfo sessionInfo) {
        if (url.startsWith("/views/anonymous/") || url.startsWith("/views/help/")) {
            LOGGER.debug("/anonymous/ or /help/");
            return true;
        } else if (url.startsWith("/views/authenticated/") && (sessionInfo.isExaminer() ||
                sessionInfo.isCommitteeMember() || sessionInfo.isDeanery()) || sessionInfo.isAdmin()) {
            LOGGER.debug("/authenticated/");
            return true;
        } else if (url.startsWith("/views/examineCommittee/") && (sessionInfo.isCommitteeMember() ||
                sessionInfo.isDeanery()) || sessionInfo.isAdmin()) {
            LOGGER.debug("/examineCommittee/");
            return true;
        } else if (url.startsWith("/views/deanery/") && (sessionInfo.isDeanery() || sessionInfo.isAdmin()) ){
            LOGGER.debug("/deanery/");
            return true;
        }
        // Deny access to all other pages
        return false;
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
        return PhaseId.RESTORE_VIEW;
    }
    private void redirectTo404Page(ExternalContext ctx) {
        try {
            ctx.redirect("/errorPage.xhtml");
        } catch (IOException e) {
            LOGGER.error("Error redirecting to 404 page: {}", e.getMessage());
        }
    }
}
