package dtt.business.utilities;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * The TrespassListener class implements the PhaseListener interface to handle
 * authorization and access control for JSF pages.
 * @author Johannes Silvennoinen
 */
public class TrespassListener implements PhaseListener {
    private static final Logger LOGGER = LogManager.getLogger(TrespassListener.class);
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
        LOGGER.info("afterPhase started!");

        final FacesContext fctx = phaseEvent.getFacesContext();
        final ExternalContext ctx = fctx.getExternalContext();
        final Map<String, Object> sessionMap = ctx.getSessionMap();

        boolean publicArea = false;
        final UIViewRoot viewRoot = fctx.getViewRoot();
        if (viewRoot != null) {
            final String url = viewRoot.getViewId();
            publicArea = url.endsWith("login.xhtml");
        }

        final boolean loggedIn = sessionMap.containsKey("logged in");

        if (!publicArea && !loggedIn) {
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have to log in first.", null);
            fctx.addMessage(null, fmsg);

            ctx.getFlash().setKeepMessages(true);

            NavigationHandler navigationHandler = fctx.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(fctx, null, "login.xhtml?faces-redirect=true");

            fctx.responseComplete();
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
}
