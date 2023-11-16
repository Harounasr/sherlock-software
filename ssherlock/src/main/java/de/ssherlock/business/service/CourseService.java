package de.ssherlock.business.service;


import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * The CourseService class provides functionality for managing courses and related operations.
 */
@Named
@Dependent
public class CourseService {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private final Logger logger;

    /**
     * Constructs a CourseService with the specified logger.
     *
     * @param logger The logger to be used for logging messages related to CourseService.
     */
    @Inject
    public CourseService(Logger logger) {
        this.logger = logger;
    }

    /**
     * Retrieves a list of all courses.
     *
     * @return A list of all courses.
     */
    public List<Course> getCourses() {
        return null;
    }

    /**
     * Retrieves a list of courses associated with the specified user.
     *
     * @param user The user for whom to retrieve courses.
     * @return A list of courses associated with the user.
     */
    public List<Course> getCourses(User user) {
        return null;
    }

    /**
     * Retrieves a course with the specified course name.
     *
     * @param courseName The name of the course to retrieve.
     * @return The course with the specified name.
     */
    public Course getCourse(String courseName) {
        return null;
    }

    /**
     * Adds a new course.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {

    }

    /**
     * Removes an existing course.
     *
     * @param course The course to remove.
     */
    public void removeCourse(Course course) {

    }

    /**
     * Updates the role of a user in a specific course.
     *
     * @param user       The user for whom to update the role.
     * @param courseRole The new role for the user in the course.
     */
    public void updateCourseRole(User user, CourseRole courseRole) {

    }
}
