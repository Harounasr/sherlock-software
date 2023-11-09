package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class VerificationBean {

    @Inject
    private Logger logger;

    @Inject
    private AppSession session;

    public VerificationBean() {

    }
    @PostConstruct
    public void handleVerifiedRegistration() {

    }

    public String navigateToLogin() {
        return "";
    }

}
