package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Logger;

/**
 *
 * NOT INCLUDED IN ACTUAL IMPLEMENTATION
 * FOR PROTOTYPING ONLY!!
 *
 */

@Named
@RequestScoped
public class ImageUploadPrototypeBean {

    private final SystemService systemService;
    private final AppSession appSession;
    private final Logger logger;

    private Part uploadedFile;

    private SystemSettings systemSettings;

    private String logoImage;

    @Inject
    public ImageUploadPrototypeBean(SystemService systemService, AppSession appSession, Logger logger) {
        this.systemService = systemService;
        this.appSession = appSession;
        this.logger = logger;
    }

    @PostConstruct
    public void initialize() {
        systemSettings = systemService.getSystemSettings();
        logoImage = Base64.getEncoder().encodeToString(systemSettings.getLogo());
    }

    public void handleUpload() {
        if (uploadedFile != null) {
            try {
                InputStream inputStream = uploadedFile.getInputStream();
                systemSettings.setLogo(inputStream.readAllBytes());
                systemService.updateSystemSettings(systemSettings);
                initialize();
            } catch (IOException e) {
            }
        }
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}
