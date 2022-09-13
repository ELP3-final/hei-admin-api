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
import school.hei.haapi.endpoint.rest.api.UsersApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Event;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.time.Instant;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.BAD_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.EVENT1_ID;
import static school.hei.haapi.integration.conf.TestUtils.MANAGER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TEACHER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsForbiddenException;
import static school.hei.haapi.integration.conf.TestUtils.isValidUUID;
import static school.hei.haapi.integration.conf.TestUtils.setUpCognito;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = FeeIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class EventIT {
    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, EventIT.ContextInitializer.SERVER_PORT);
    }

    static Event event1() {
        Event event = new Event();
        event.setId("EVENT1_ID");
        event.setName("DONNEE 2");
        event.setType(Event.TypeEnum.COURSE);
        event.setDate(Instant.parse("2021-11-08T08:25:24.00Z"));
        event.setStartTime("8h");
        event.setFinishTime("10h");
        event.setSupervisor(Event.SupervisorEnum.TEACHER);
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE1_ID");
        event.setGroupId("GROUP1_ID");
        return event;
    }

    static Event event2() {
        Event event = new Event();
        event.setId("EVENT2_ID");
        event.setName("SYS 2");
        event.setType(Event.TypeEnum.COURSE);
        event.setDate(Instant.parse("2021-11-08T08:25:24.00Z"));
        event.setStartTime("10h");
        event.setFinishTime("12h");
        event.setSupervisor(Event.SupervisorEnum.TEACHER);
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE2_ID");
        event.setGroupId("GROUP2_ID");
        return event;
    }


    public static Event someCreatableEvent() {
        Event event = new Event();
        event.setName("SYS 2" + randomUUID());
        event.setType(Event.TypeEnum.COURSE);
        event.setDate(Instant.parse("2021-11-08T08:25:24.00Z"));
        event.setStartTime("10h");
        event.setFinishTime("12h");
        event.setSupervisor(Event.SupervisorEnum.TEACHER);
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE2_ID");
        event.setGroupId("GROUP2_ID");
        return event;
    }

    @BeforeEach
    public void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        TeachingApi api = new TeachingApi(anonymousClient);
        assertThrowsForbiddenException(api::getEvents);
    }

    @Test
    void badtoken_write_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        TeachingApi api = new TeachingApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.createOrUpdateGroups(List.of()));
    }

    @Test
    void student_read_own_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        UsersApi api = new UsersApi(student1Client);
        Event actual = api.getEventById(EVENT1_ID);

        assertEquals(event1(), actual);
    }

    @Test
    void student_read_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        UsersApi api = new UsersApi(student1Client);

        assertThrowsForbiddenException(() -> api.getEventById(TestUtils.EVENT2_ID));

        assertThrowsForbiddenException(
                () -> api.getEvents(1, 20, Event.StatusEnum.ONGOING));
    }

    @Test
    void student_write_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);

        TeachingApi api = new TeachingApi(student1Client);
        assertThrowsForbiddenException(() -> api.createOrUpdateEvents(List.of()));
    }

    @Test
    void teacher_write_ko() {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);

        TeachingApi api = new TeachingApi(teacher1Client);
        assertThrowsForbiddenException(() -> api.createOrUpdateEvents(List.of()));
    }

    @Test
    void manager_write_create_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        Event toCreate3 = someCreatableEvent();
        Event toCreate4 = someCreatableEvent();

        TeachingApi api = new TeachingApi(manager1Client);
        List<Event> created = api.createOrUpdateEvents(List.of(toCreate3, toCreate4));

        assertEquals(2, created.size());
        Event created3 = created.get(0);
        assertTrue(isValidUUID(created3.getId()));
        toCreate3.setId(created3.getId());
        assertNotNull(created3.getDate());
    }

    @Test
    void manager_write_update_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        TeachingApi api = new TeachingApi(manager1Client);
        List<Event> toUpdate = api.createOrUpdateEvents(List.of(
                someCreatableEvent(),
                someCreatableEvent()));
        Event toUpdate0 = toUpdate.get(0);
        toUpdate0.setName("A new name zero");
        Event toUpdate1 = toUpdate.get(1);
        toUpdate1.setName("A new name one");

        List<Event> updated = api.createOrUpdateEvents(toUpdate);

        assertEquals(2, updated.size());
        assertTrue(updated.contains(toUpdate0));
        assertTrue(updated.contains(toUpdate1));
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
