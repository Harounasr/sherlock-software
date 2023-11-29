package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for the coursePagination.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class CoursePaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size for the pagination.
     */
    private static final int PAGE_SIZE = 2;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service to handle Course-related actions.
     */
    private final CourseService courseService;

    /**
     * The new course that the user creates.
     */
    private Course newCourse;

    /**
     * List of courses to be displayed.
     */
    private List<Course> courses;


    /**
     * Constructs a CoursesPaginationBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The CourseService (Injected).
     * @param newCourse     Empty injected DTO.
     */
    @Inject
    public CoursePaginationBean(SerializableLogger logger, AppSession appSession, CourseService courseService, Course newCourse) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
        this.newCourse = newCourse;
    }

    /**
     * Initializes the CoursesPaginationBean after construction.
     * Retrieves the list of courses for pagination.
     */
    @PostConstruct
    public void initialize() {
        loadData();
        setPageSize(PAGE_SIZE);
        setCurrentIndex(0);
        setLastIndex(courses.size() - 1);
        logger.log(Level.INFO, String.valueOf(courses.size()));
    }

    /**
     * Navigates to the selected course.
     *
     * @param course The selected course.
     * @return The navigation outcome.
     */
    public String select(Course course) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("courseName", course.getName());
        logger.log(Level.INFO, "Selected Course: " + course.getName());
        return "/view/registered/course.xhtml?faces-redirect=true&Id=" + course.getName();
    }

    /**
     * Adds the newly created course to the database.
     */
    public void addCourse() {

    }

    /**
     * Gets courses.
     *
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets courses.
     *
     * @param courses the courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets new course.
     *
     * @return the new course
     */
    public Course getNewCourse() {
        return newCourse;
    }

    /**
     * Sets new course.
     *
     * @param newCourse the new course
     */
    public void setNewCourse(Course newCourse) {
        this.newCourse = newCourse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        courses = courseService.getCourses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void filterBy() {

    }

}
