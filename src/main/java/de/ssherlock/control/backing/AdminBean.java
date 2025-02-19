package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Backing Bean for the admin.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class AdminBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active Session.
     */
    private final AppSession appSession;

    /**
     * The page to be loaded into the content section.
     */
    private String targetPage;

    /**
     * Constructs an AdminBean.
     *
     * @param logger     The logger used for logging within this class (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public AdminBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    public void initialize() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (sessionMap.containsKey("selectedPage")) {
            targetPage = (String) sessionMap.get("selectedPage");
        } else {
            targetPage = "adminSettings.xhtml";
        }
    }

    /**
     * Gets target page.
     *
     * @return the target page
     */
    public String getTargetPage() {
        return targetPage;
    }

    /**
     * Sets target page.
     *
     * @param targetPage the target page
     */
    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("selectedPage", targetPage);
    }
}
