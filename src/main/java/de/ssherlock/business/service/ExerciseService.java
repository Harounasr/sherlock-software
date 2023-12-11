package de.ssherlock.business.service;

import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.exception.PersistenceNonExistentExerciseException;
import de.ssherlock.persistence.repository.ExerciseRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * The ExerciseService class provides functionality for managing exercises and related operations.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class ExerciseService implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to ExerciseService. */
  private final SerializableLogger logger;

  /** The connection pool instance. */
  private final ConnectionPool connectionPool;

  /**
   * Constructs an ExerciseService with the specified logger.
   *
   * @param logger The logger to be used for logging messages related to ExerciseService.
   * @param connectionPool The connection pool.
   */
  @Inject
  public ExerciseService(SerializableLogger logger, ConnectionPool connectionPool) {
    this.logger = logger;
    this.connectionPool = connectionPool;
  }

  /**
   * Retrieves a list of exercises associated with the specified course.
   *
   * @param course The course for which to retrieve exercises.
   * @return A list of exercises associated with the course.
   */
  public List<Exercise> getExercises(Course course) {
    Connection connection = connectionPool.getConnection();
    ExerciseRepository exerciseRepository =
        RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);

    List<Exercise> exercises = exerciseRepository.getExercises(course);
    connectionPool.releaseConnection(connection);
    return exercises;
  }

  /**
   * Updates the information of an existing exercise.
   *
   * @param exercise The exercise to be updated.
   * @throws BusinessNonExistentExerciseException when the exercise does not exist in the database.
   */
  public void updateExercise(Exercise exercise) throws BusinessNonExistentExerciseException {
    Connection connection = connectionPool.getConnection();
    ExerciseRepository exerciseRepository =
        RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);
    try {
      exerciseRepository.updateExercise(exercise);
    } catch (PersistenceNonExistentExerciseException e) {
      throw new BusinessNonExistentExerciseException("", e);
    }
  }

  /**
   * Adds a new exercise.
   *
   * @param exercise The exercise to be added.
   */
  public void addExercise(Exercise exercise) {
    Connection connection = connectionPool.getConnection();
    ExerciseRepository exerciseRepository =
        RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);
    exerciseRepository.insertExercise(exercise);
  }

  /**
   * Removes an existing exercise.
   *
   * @param exercise The exercise to be removed.
   * @throws BusinessNonExistentExerciseException when the exercise does not exist in the database.
   */
  public void removeExercise(Exercise exercise) throws BusinessNonExistentExerciseException {
    Connection connection = connectionPool.getConnection();
    ExerciseRepository exerciseRepository =
        RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);
    try {
      exerciseRepository.deleteExercise(exercise);
    } catch (PersistenceNonExistentExerciseException e) {
      throw new BusinessNonExistentExerciseException();
    }
  }

  /**
   * Retrieves an exercise based on its unique identifier.
   *
   * @param exercise The exercise to get.
   * @return The exercise with the specified identifier.
   * @throws BusinessNonExistentExerciseException when the exercise does not exist in the database.
   */
  public Exercise getExercise(Exercise exercise) throws BusinessNonExistentExerciseException {
    Connection connection = connectionPool.getConnection();
    ExerciseRepository exerciseRepository =
        RepositoryFactory.getExerciseRepository(RepositoryType.POSTGRESQL, connection);
    try {
      exercise = exerciseRepository.getExercise(exercise);
    } catch (PersistenceNonExistentExerciseException e) {
      throw new BusinessNonExistentExerciseException(e.getMessage(), e);
    }
    return exercise;
  }
}
