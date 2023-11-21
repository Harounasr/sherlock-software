package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing bean for the registration.xhtml facelet.
 */
@Named
@ViewScoped
public class RegistrationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The service handling user-related operations.
     */
    private final UserService userService;

    /**
     * The user that is to be registered.
     */
    private User user;

    /**
     * The unhashed password entered by the user.
     */
    private String unhashedPassword;

    /**
     * Constructs a new Registration bean.
     *
     * @param logger      The logger for this class (Injected).
     * @param appSession  The active session (Injected).
     * @param userService The service for user-based operations (Injected).
     * @param user        The user that is to be registered (Injected empty).
     */
    @Inject
    public RegistrationBean(SerializableLogger logger, AppSession appSession, UserService userService, User user) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.user = user;
    }

    /**
     * Tries to register a new user using the provided information.
     */
    public void register() {
        Password password = PasswordHashing.getHashedPassword(unhashedPassword);
        user.setPassword(password);
        userService.registerUser(user);
    }

    /**
     * Navigates to the login page.
     *
     * @return String representing the navigation outcome.
     */
    public String navigateToLogin() {
        logger.log(Level.INFO, "Login");
        return "/view/login.xhtml";
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets unhashed password.
     *
     * @return the unhashed password
     */
    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    /**
     * Sets unhashed password.
     *
     * @param unhashedPassword the unhashed password
     */
    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }
}
