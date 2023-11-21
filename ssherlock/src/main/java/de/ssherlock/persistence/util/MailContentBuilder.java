package de.ssherlock.persistence.util;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Utility class for building content for different types of emails.
 */
public class MailContentBuilder {

    /**
     * Builds the content for a verification email.
     *
     * @param user The user for whom the verification email is generated.
     * @return The content of the verification email.
     */
    public static String buildVerificationMail(User user) {
        return "Hi " + user.getUsername() +  ".\n Thank you for registration.\n" +
                "Click the Link below in order to verify your account.\n" +
                "http://localhost:8016/ssherlock_war_exploded/view/verification.xhtml?token=" + generateToken();
    }

    /**
     * Builds the content for a password reset email.
     *
     * @param user The user for whom the password reset email is generated.
     * @return The content of the password reset email.
     */
    public static String buildPasswordResetMail(User user) {
        return null;
    }

    /**
     * Builds the content for a reminder email related to a specific exercise.
     *
     * @param user     The user to whom the reminder email is sent.
     * @param exercise The exercise for which the reminder is generated.
     * @return The content of the reminder email.
     */
    public static String buildReminderMail(User user, Exercise exercise) {
        return null;
    }

    private static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);

        // Convert the random bytes to a hexadecimal string
        BigInteger tokenNumber = new BigInteger(1, tokenBytes);
        String token = tokenNumber.toString(16);

        // Ensure that the token has the desired length
        while (token.length() < 32) {
            token = "0" + token;
        }

        return token;
    }


}

