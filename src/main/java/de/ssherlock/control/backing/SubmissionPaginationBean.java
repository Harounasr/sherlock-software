package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessDBAccessException;
import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.exception.BusinessNonExistentExerciseException;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.SubmissionService;
import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for the allSubmissionPaginationBean.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class SubmissionPaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size for the pagination.
     */
    private static final int PAGE_SIZE = 6;

    /**
     * The {@code Logger} instance to be used in this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * The parent backing bean.
     */
    private final ExerciseBean exerciseBean;

    /**
     * The Exercise service.
     */
    private final ExerciseService exerciseService;

    /**
     * Service that provides submission-based actions.
     */
    private final SubmissionService submissionService;

    /**
     * List of all submissions.
     */
    private List<Submission> submissions;

    /**
     * The current exercise.
     */
    private Exercise exercise;

    /**
     * The users role in the current course.
     */
    private CourseRole courseRole;

    /**
     * Constructs an AllSubmissionPaginationBean.
     *
     * @param logger            The logger used for logging within this class (Injected).
     * @param appSession        The active session (Injected).
     * @param submissionService The SubmissionService used for submission-related actions (Injected).
     * @param exerciseBean      The parent bean.
     * @param exerciseService   The exercise service.
     */
    @Inject
    public SubmissionPaginationBean(
            SerializableLogger logger, AppSession appSession, SubmissionService submissionService, ExerciseBean exerciseBean,
            ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.submissionService = submissionService;
        this.exerciseBean = exerciseBean;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the AllSubmissionPaginationBean after construction. Retrieves all submissions upon
     * creation.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setCurrentIndex(0);
        getPagination().setPageSize(PAGE_SIZE);
        exercise = new Exercise();
        exercise.setId(exerciseBean.getExerciseId());
        courseRole = exerciseBean.getUserCourseRole();
        try {
            exercise = exerciseService.getExercise(exercise);
        } catch (BusinessNonExistentExerciseException e) {
            throw new NoAccessException("The exercise does not exist anymore.", e);
        }
        loadData();
        getPagination().setLastIndex(submissions.size() - 1);
        logger.finest("Initialized SubmissionPaginationBean.");
    }

    /**
     * Action to redirect the user to the selected submission.
     *
     * @param submissionId the id of the submission.
     */
    public void selectSubmission(long submissionId) {
        switch (courseRole) {
        case TUTOR:
        case TEACHER:
            exerciseBean.setSubmissionId(submissionId);
            exerciseBean.setTargetPage("testate.xhtml");
            break;
        case MEMBER:
            exerciseBean.setSubmissionId(submissionId);
            exerciseBean.setTargetPage("submission.xhtml");
            break;
        default:
            if (appSession.isAdmin()) {
                exerciseBean.setSubmissionId(submissionId);
                exerciseBean.setTargetPage("testate.xhtml");
                break;
            } else {
                throw new NoAccessException("You are not authorized to view this submission");
            }
        }
    }

    /**
     * Gets submissions.
     *
     * @return the submissions
     */
    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Sets submissions.
     *
     * @param submissions the submissions
     */
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        try {
            if (appSession.isAdmin()) {
                submissions = submissionService.getSubmissions(getPagination(), exercise);
            } else {
                submissions = switch (courseRole) {
                    case TEACHER -> submissionService.getSubmissions(getPagination(), exercise);
                    case TUTOR -> submissionService.getSubmissionsForTutor(getPagination(), appSession.getUser(), exercise);
                    case MEMBER -> submissionService.getSubmissionsForStudent(getPagination(), appSession.getUser(), exercise);
                    default -> Collections.emptyList();
                };
            }
        } catch (BusinessDBAccessException | BusinessNonExistentCourseException e) {
            submissions = Collections.emptyList();
            Notification notification = new Notification("The submissions could not be loaded", NotificationType.ERROR);
            notification.generateUIMessage();
            logger.log(Level.WARNING, "There was a problem loading the submissions.", e);
        }
        logger.finest("Loaded submissions");
    }

    /**
     * Whether user is member.
     *
     * @return is member
     */
    public boolean isMember() {
        return courseRole == CourseRole.MEMBER;
    }

    /**
     * Whether user is tutor.
     *
     * @return is member
     */
    public boolean isTutor() {
        return courseRole == CourseRole.TUTOR;
    }

    /**
     * Whether user is teacher.
     *
     * @return is teacher.
     */
    public boolean isTeacher() {
        return courseRole == CourseRole.TEACHER;
    }

    /**
     * Whether the user is admin.
     *
     * @return is admin.
     */
    public boolean isAdmin() {
        return appSession.isAdmin();
    }
}
