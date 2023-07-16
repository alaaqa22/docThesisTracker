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
        final FacesContext fctx = phaseEvent.getFacesContext();
        final ExternalContext ctx = fctx.getExternalContext();
        final SessionInfo sessionInfo = CDI.current().select(SessionInfo.class).get();

        final UIViewRoot viewRoot = fctx.getViewRoot();
        if (viewRoot != null) {
            final String url = viewRoot.getViewId();
            final boolean isAllowed = checkAccessControl(url, sessionInfo);
            if (!isAllowed) {
                // User is not allowed to access this page.
                if (sessionInfo.isLoggedIn()) {
                    LOGGER.info("User found trespassing: " + sessionInfo.getUser().getEmail());
                    LOGGER.debug("Redirecting to circulation list.");
                    // Message field for whoever wants to add a way to add the message in the header post redirect.
                    redirectToCirculationList(ctx, "Sie dürfen nicht auf die Seite zugreifen.");
                    fctx.responseComplete();
                } else {
                    LOGGER.info("Anonymous user tried to trespass, redirecting to login page.");
                    // Message field for whoever wants to add a way to add the message in the header post redirect.
                    redirectToLoginPage(ctx,
                            "Sie dürfen auf die angeforderte Seite nicht zugreifen, bitte melden Sie sich an.");
                    fctx.responseComplete();
                }
            }
        }
    }


    /**
     * Checks if the currently logged-in user is supposed to have access to the current page.
     * @param url the current url
     * @param sessionInfo the session information of the logged-in user.
     * @return true if the user is allowed to visit the page.
     */
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
        } else if (url.startsWith("/views/admin/") && sessionInfo.isAdmin()) {
            LOGGER.debug("/admin/");
            return true;
        } else {
            // Deny access to all other pages
            return false;
        }

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

    /**
     * Redirects the user to the circulation list.
     * @param ctx the external context that is used to redirect.
     */
    private void redirectToCirculationList(ExternalContext ctx, String message) {
        try {
            ctx.redirect(ctx.getRequestContextPath() + "/views/authenticated/circulationslist.xhtml");
        } catch (IOException e) {
            LOGGER.error("Error redirecting to CirculationList page: " + e.getMessage());
        }
    }

    /**
     * Redirects the user to the login page.
     * @param ctx the external context that is used to redirect.
     */
    private void redirectToLoginPage(ExternalContext ctx, String message) {
        try {
            ctx.redirect(ctx.getRequestContextPath() + "/views/anonymous/login.xhtml");
        } catch (IOException e) {
            LOGGER.error("Error redirecting to login page: " + e.getMessage());
        }
    }
}
