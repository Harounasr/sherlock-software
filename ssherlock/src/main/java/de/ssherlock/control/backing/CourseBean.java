package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.service.CourseService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Backing bean for course.xhtml facelet.
 */
@Named
@ViewScoped
public class CourseBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service responsible for managing courses.
     */
    private final CourseService courseService;

    /**
     * Service responsible for managing exercises.
     */
    private final ExerciseService exerciseService;

    /**
     * Service responsible for managing users.
     */
    private final UserService userService;

    private Course course;

    /**
     * Constructs a CourseBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param courseService   The service responsible for managing courses (Injected).
     * @param exerciseService The service responsible for managing exercises (Injected).
     * @param userService     The service responsible for managing users (Injected).
     */
    @Inject
    public CourseBean(SerializableLogger logger, AppSession appSession, CourseService courseService,
                      ExerciseService exerciseService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    /**
     * Initializes the CourseBean after construction.
     * Performs necessary setup actions for course-related functionalities.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        String courseId = requestParams.get("Id");
        try {
            course = courseService.getCourse(courseId);
        } catch (BusinessNonExistentCourseException e) {
            throw new RuntimeException(e);
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
