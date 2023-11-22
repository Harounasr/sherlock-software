package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentImageException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
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
 * The ExerciseDescriptionImageService class provides functionality
 * for managing ExerciseDescriptionImages and related operations.
 */
@Named
@Dependent
public class ExerciseDescriptionImageService implements Serializable {

    /**
     * Serial Version UID
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
    private final ConnectionPoolPsql connectionPoolPsql;

    /**
     * Constructs a ExerciseDescriptionImageService.
     *
     * @param logger The logger instance for this class.
     * @param connectionPoolPsql The connection pool instance.
     */
    @Inject
    public ExerciseDescriptionImageService(SerializableLogger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }

    /**
     * Inserts an ExerciseDescriptionImage into the database.
     *
     * @param image The ExerciseDescriptionImage to insert.
     */
    public void insertImage(ExerciseDescriptionImage image) {
        Connection connection = connectionPoolPsql.getConnection();
        ExerciseDescriptionImageRepository imageRepository = RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
        String uuid = imageRepository.insertExerciseDescriptionImage(image);
        connectionPoolPsql.releaseConnection(connection);
    }

    /**
     * Gets an ExerciseDescriptionImage from the database by its uuid.
     *
     * @param uuid The UUID of the image.
     * @return The image.
     *
     * @throws BusinessNonExistentImageException when the image does not exist in the database.
     */
    public ExerciseDescriptionImage getImage(String uuid) throws BusinessNonExistentImageException {
        Connection connection = connectionPoolPsql.getConnection();
        ExerciseDescriptionImageRepository imageRepository = RepositoryFactory.getExerciseDescriptionImageRepository(RepositoryType.POSTGRESQL, connection);
        ExerciseDescriptionImage image;
        image = imageRepository.getExerciseDescriptionImage(uuid);
        connectionPoolPsql.releaseConnection(connection);
        return image;
    }

}
