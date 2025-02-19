package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentImageException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentImageException;
import de.ssherlock.persistence.repository.ExerciseDescriptionImageRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;

/**
 * The ExerciseDescriptionImageService class provides functionality for managing
 * ExerciseDescriptionImages and related operations.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class ExerciseDescriptionImageService implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger;

    /**
     * The connection pool instance.
     */
    private final ConnectionPool connectionPool;

    /**
     * Constructs a ExerciseDescriptionImageService.
     *
     * @param logger         The logger instance for this class.
     * @param connectionPool The connection pool instance.
     */
    @Inject
    public ExerciseDescriptionImageService(SerializableLogger logger, ConnectionPool connectionPool) {
        this.logger = logger;
        this.connectionPool = connectionPool;
    }

    /**
     * Inserts an ExerciseDescriptionImage into the database.
     *
     * @param image The ExerciseDescriptionImage to insert.
     */
    public void insertImage(ExerciseDescriptionImage image) {
        logger.fine("Service request for inserting image " + image.getUUID());
        Connection connection = connectionPool.getConnection();
        ExerciseDescriptionImageRepository imageRepository =
                RepositoryFactory.getExerciseDescriptionImageRepository(
                        RepositoryType.POSTGRESQL, connection);
        imageRepository.insertExerciseDescriptionImage(image);
        connectionPool.releaseConnection(connection);
    }

    /**
     * Gets an ExerciseDescriptionImage from the database by its uuid.
     *
     * @param image The image to retrieve.
     * @return The image.
     * @throws BusinessNonExistentImageException when the image does not exist in the database.
     */
    public ExerciseDescriptionImage getImage(ExerciseDescriptionImage image)
            throws BusinessNonExistentImageException {
        logger.fine("Service request for getting image " + image.getUUID());
        Connection connection = connectionPool.getConnection();
        ExerciseDescriptionImageRepository imageRepository =
                RepositoryFactory.getExerciseDescriptionImageRepository(
                        RepositoryType.POSTGRESQL, connection);
        try {
            image = imageRepository.getExerciseDescriptionImage(image);
        } catch (PersistenceNonExistentImageException e) {
            throw new BusinessNonExistentImageException("The image was not stored in the database.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return image;
    }
}
