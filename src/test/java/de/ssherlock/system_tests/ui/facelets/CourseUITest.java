package de.ssherlock.system_tests.ui.facelets;


import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * UI Test for {@code course.xhtml}.
 * As usage of exercisePagination and courseUserPagination buttons are tested in
 * ExercisePaginationUITest and CourseUserPaginationUITest, only deleteCourse is being tested here.
 *
 * @author Leon Höfling
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseUITest extends AbstractSeleniumUITest {

    /**
     * Test for deleting a course while being admin.
     */
    @Test
    void testDeleteCourseAdmin() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=5");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Delete");
        SeleniumUITestUtils.clickOnElementWithClass(getWait(), "reallydeletebutton");
    }

    /**
     * Test for deleting a course while being teacher in this course.
     */
    @Test
    void testDeleteCourseTeacher() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.TEACHER_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=2");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Delete");
        SeleniumUITestUtils.clickOnElementWithClass(getWait(), "reallydeletebutton");
    }


}
