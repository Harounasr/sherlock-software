package de.ssherlock.business.maintenance;

import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.exception.MailUnavailableException;
import jakarta.enterprise.inject.spi.CDI;

import java.util.logging.Level;

/**
 * Automatically sends an E-Mail to a user.
 *
 * @author Leon Höfling
 */
public class SendEmailNotificationEvent implements Runnable {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(SendEmailNotificationEvent.class);

    /**
     * The User-service for this class.
     */
    private final UserService userService;

    /**
     * Constructs a new SendEmailNotificationEvent.
     */
    public SendEmailNotificationEvent() {
        userService = CDI.current().select(UserService.class).get();
    }

    /**
     * Sends email notifications.
     */
    @Override
    public void run() {
        try {
            LOGGER.info("sending emails");
            userService.sendReminderEmail();
        } catch (MailUnavailableException e) {
            LOGGER.log(Level.SEVERE, "sending emails encountered an exception.", e);
        }
    }
}
