package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Named
@RequestScoped
public class CourseBean {
    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private CourseService courseService;
    @Inject
    private ExerciseService exerciseService;
    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;

    public CourseBean() {

    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
    }

    public Map<User, CourseRole> getUserRoles() {
        return userRoles;
    }

    public void setUsers(Map<User, CourseRole> users) {
        this.userRoles = users;
    }

}
