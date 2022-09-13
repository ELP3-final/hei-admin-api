package school.hei.haapi.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.TeachingApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.BAD_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.COURSE1_ID;
import static school.hei.haapi.integration.conf.TestUtils.COURSE2_ID;
import static school.hei.haapi.integration.conf.TestUtils.COURSE3_ID;
import static school.hei.haapi.integration.conf.TestUtils.MANAGER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TEACHER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsForbiddenException;
import static school.hei.haapi.integration.conf.TestUtils.isValidUUID;
import static school.hei.haapi.integration.conf.TestUtils.setUpCognito;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = CourseIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class CourseIT {
    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, CourseIT.ContextInitializer.SERVER_PORT);
    }

    static Course course1() {
        return new Course()
                .id(COURSE1_ID)
                .name("PROG1")
                .ref("Algorithmic")
                .credits(15)
                .totalHours(20);
    }

    static Course course2() {
        return new Course()
                .id(COURSE2_ID)
                .name("Oriented Object Programming")
                .ref("PROG2")
                .credits(14)
                .totalHours(25);
    }

    static Course course3() {
        return new Course()
                .id(COURSE3_ID)
                .name("PWA / SPA")
                .ref("EL1P2")
                .credits(13)
                .totalHours(24);
    }


    public static Course someCreatableCourse() {
        Course course= new Course();
        course.setName("Some name");
        course.setRef("CRs-" + randomUUID());
        course.setCredits(3);
        course.setTotalHours(30);
        return course;
    }

    @BeforeEach
    void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        TeachingApi api = new TeachingApi(anonymousClient);
        assertThrowsForbiddenException(api::getCourses);
    }

    @Test
    void badtoken_write_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        TeachingApi api = new TeachingApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.createOrUpdateGroups(List.of()));
    }

    @Test
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        TeachingApi api = new TeachingApi(student1Client);
        Course actual1 = api.getCourseById(COURSE1_ID);
        List<Course> actualCourse = api.getCourses();

        assertEquals(course1(), actual1);
        assertTrue(actualCourse.contains(course1()));
        assertTrue(actualCourse.contains(course1()));
    }

    @Test
    void student_write_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        TeachingApi api = new TeachingApi(student1Client);
        assertThrowsForbiddenException(() -> api.createOrUpdateGroups(List.of()));
    }

    @Test
    void teacher_write_ko() {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);

        TeachingApi api = new TeachingApi(teacher1Client);
        assertThrowsForbiddenException(() -> api.createOrUpdateGroups(List.of()));
    }
    @Test
    void manager_write_create_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        Course toCreate3 = someCreatableCourse();

        TeachingApi api = new TeachingApi(manager1Client);
        Course created = api.createOrUpdateCourses(toCreate3);

        assertNotNull(created);
        Course created3 = created;
        assertTrue(isValidUUID(created3.getId()));
        toCreate3.setId(created3.getId());
        assertNotNull(created3.getId());
    }
    @Test
    void manager_write_update_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        TeachingApi api = new TeachingApi(manager1Client);
        Course toUpdate = api.createOrUpdateCourses(
                someCreatableCourse());
        Course toUpdate0 = toUpdate;
        toUpdate0.setName("A new name zero");

        Course updated = api.createOrUpdateCourses(toUpdate);

        assertNotNull(updated);
    }


    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
