package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for the exercisePagination.xhtml facelet.
 *
 * @author Haroun Alswedany
 */
@Named
@ViewScoped
public class ExercisePaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The page size for the pagination.
     */
    private static final int PAGE_SIZE = 10;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service for handling Exercise-related actions.
     */
    private final ExerciseService exerciseService;

    /**
     * The parent bean.
     */
    private final CourseBean courseBean;

    /**
     * The current course.
     */
    private List<Exercise> exercises;

    /**
     * The new Exercise.
     */
    private Exercise exercise;

    /**
     * Constructs an ExercisePaginationBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     * @param courseBean The parent bean.
     */
    @Inject
    public ExercisePaginationBean(
            SerializableLogger logger, AppSession appSession, ExerciseService exerciseService, CourseBean courseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
        this.courseBean = courseBean;
    }

    /**
     * Initializes the ExercisePaginationBean after construction. Retrieves the exercises from the service.
     */
    @PostConstruct
    public void initialize() {
        exercise = new Exercise();
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setSortBy("name");
        loadData();
        getPagination().setLastIndex(exercises.size() - 1);
    }

    /**
     * Navigates to the selected exercise.
     *
     * @param exercise The selected exercise.
     * @return The navigation outcome.
     */
    public String select(Exercise exercise) {
        logger.info("Selected Exercise: " + exercise.getName());
        return "/view/registered/exercise.xhtml?faces-redirect=true&Id=" + exercise.getId();
    }

    /**
     * Gets exercises.
     *
     * @return the exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Sets exercises.
     *
     * @param exercises the exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Adds an exercise to the database.
     */
    public void addExercise() {
        logger.log(Level.INFO, "try to add a new exercise.");
        if (exerciseAlreadyExists(exercise.getName())) {
            Notification notification = new Notification("Exercise with the same name already exists.", NotificationType.ERROR);
            notification.generateUIMessage();
        } else {
            exercise.setCourseId(courseBean.getCourse().getId());
            exerciseService.addExercise(exercise);
            logger.log(Level.INFO, "Exercise Successfully added.");
            Notification notification = new Notification("Exercise Successfully added.", NotificationType.SUCCESS);
            notification.generateUIMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            context.setViewRoot(context.getApplication().getViewHandler().createView(context, viewId));
            context.getPartialViewContext().setRenderAll(true);
            context.renderResponse();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        exercises = exerciseService.getExercises(getPagination(), courseBean.getCourse());
        if (!(appSession.isAdmin() || courseBean.isTeacherRights())) {
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
            exercises = exercises.stream().filter(e -> e.getPublishDate().before(timestamp)).toList();
        }
    }

    /**
     * Gets exercise.
     *
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Sets exercise.
     *
     * @param exercise the exercise
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Checks whether the publish date of the exercise has passed.
     *
     * @param exercise The Exercise.
     * @return Whether the date has passed.
     */
    public boolean isPublishDatePast(Exercise exercise) {
        return exercise.getPublishDate().toInstant().isBefore(Calendar.getInstance().toInstant());
    }

    /**
     * Checks whether the recommended deadline of the exercise has passed.
     *
     * @param exercise The Exercise.
     * @return Whether the date has passed.
     */
    public boolean isRecommendedDeadlinePast(Exercise exercise) {
        return exercise.getRecommendedDeadline().toInstant().isBefore(Calendar.getInstance().toInstant());
    }

    /**
     * Checks whether the obligatory deadline of the exercise has passed.
     *
     * @param exercise The Exercise.
     * @return Whether the date has passed.
     */
    public boolean isObligatoryDeadlinePast(Exercise exercise) {
        return exercise.getObligatoryDeadline().toInstant().isBefore(Calendar.getInstance().toInstant());
    }

    private boolean exerciseAlreadyExists(String exerciseName) {
        for (Exercise existingExercise : exercises) {
            if (existingExercise.getName().equals(exerciseName)) {
                return true;
            }
        }
        return false;
    }
}
