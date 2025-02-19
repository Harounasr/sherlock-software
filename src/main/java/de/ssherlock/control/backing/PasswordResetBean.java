package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Backing bean for the passwordReset.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class PasswordResetBean implements Serializable {

    /** Serial Version UID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for logging events.
     */
    private final SerializableLogger logger;

    /**
     * The service for user-related operations.
     */
    private final UserService userService;

    /**
     * The new password.
     */
    private String passwordOne;

    /**
     * The repeated password.
     */
    private String passwordTwo;

    /**
     * The user for password reset.
     */
    private User user;

    /**
     * The Token given in the url.
     */
    private String token;

    /**
     * Constructor for PasswordForgottenBean.
     *
     * @param logger      The logger for logging events (Injected).
     * @param userService The service for user-related operations (Injected).
     */
    @Inject
    public PasswordResetBean(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
        user = new User();
        logger.log(Level.INFO, "Inside PasswordResetBean");
    }

    /**
     * Gets the token from the url and sets the value.
     */
    @PostConstruct
    public void setToken() {
        Map<String, String> parameter =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!Objects.equals(parameter.get("token"), "")) {
            token = parameter.get("token");
        }
    }

    /**
     * Reset the password.
     *
     * @return The destination view.
     */
    public String resetPassword() {
        if (!Objects.equals(passwordOne, passwordTwo)) {
            Notification notification = new Notification("Passwords do not match.", NotificationType.ERROR);
            notification.generateUIMessage();
            return "";
        }
        user.setVerificationToken(token);
        logger.log(Level.INFO, "Token: " + token);
        user.setPassword(PasswordHashing.hashPassword(passwordOne));
        if (userService.resetPassword(user)) {
            return "/view/public/login.xhtml?faces-redirect=true";
        } else {
            return "";
        }
    }

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user password reset.
     *
     * @param user The user for the password reset.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the new password.
     *
     * @return The new password.
     */
    public String getPasswordOne() {
        return passwordOne;
    }

    /**
     * Sets the new password.
     *
     * @param passwordOne The new password.
     */
    public void setPasswordOne(String passwordOne) {
        this.passwordOne = passwordOne;
    }

    /**
     * Gets the repeated password.
     *
     * @return The repeated password.
     */
    public String getPasswordTwo() {
        return passwordTwo;
    }

    /**
     * Sets the repeated password.
     *
     * @param passwordTwo The repeated password.
     */
    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }
}
