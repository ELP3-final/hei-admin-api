package school.hei.haapi.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.AttendingApi;
import school.hei.haapi.endpoint.rest.api.TeachingApi;
import school.hei.haapi.endpoint.rest.api.UsersApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
import school.hei.haapi.endpoint.rest.model.Event;
import school.hei.haapi.endpoint.rest.model.Place;
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
import static school.hei.haapi.integration.conf.TestUtils.PLACE1_ID;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_ID;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TEACHER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsApiException;
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
    @BeforeEach
    void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    static Event event1() {
        Event event = new Event();
        event.setId("EVENT1_ID");
        event.setName("DONNEE 2");
        event.setType(Event.TypeEnum.COURSE);
        event.setDate(Instant.parse("2021-11-08T08:25:24.00Z"));
        event.setStartTime("8h");
        event.setFinishTime("10h");
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE1_ID");
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
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE2_ID");
        return event;
    }


    public static Event someCreatableEvent() {
        Event event = new Event();
        event.setName("SYS 2" + randomUUID());
        event.setType(Event.TypeEnum.COURSE);
        event.setDate(Instant.parse("2021-11-08T08:25:24.00Z"));
        event.setStartTime("10h");
        event.setFinishTime("12h");
        event.setStatus(Event.StatusEnum.ONGOING);
        event.setPlaceId("PLACE2_ID");
        return event;
    }


    @Test
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        AttendingApi api = new AttendingApi(student1Client);

        Event actual1 = api.getEventsById(EVENT1_ID);
        List<Event> actualEvents = api.getEvents(1,10);

        assertEquals(event1(), actual1);
        assertTrue(actualEvents.contains(event1()));
        assertTrue(actualEvents.contains(event2()));
    }

    @Test
    void manager_read_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        AttendingApi api = new AttendingApi(manager1Client);

        Event actual1 = api.getEventsById(EVENT1_ID);
        List<Event> actualEvents = api.getEvents(1,10);

        assertEquals(event1(), actual1);
        assertTrue(actualEvents.contains(event1()));
        assertTrue(actualEvents.contains(event2()));
    }

    @Test
    void teacher_read_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        AttendingApi api = new AttendingApi(teacher1Client);

        Event actual1 = api.getEventsById(EVENT1_ID);
        List<Event> actualEvents = api.getEvents(1,10);

        assertEquals(event1(), actual1);
        assertTrue(actualEvents.contains(event1()));
        assertTrue(actualEvents.contains(event2()));
    }

    @Test
    void manager_write_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        Event toCreate3 = someCreatableEvent();
        Event toCreate4 = someCreatableEvent();

        AttendingApi api = new AttendingApi(manager1Client);
        List<Event> created = api.saveOrUpdateEvents(PLACE1_ID,List.of(toCreate3, toCreate4));

        assertEquals(2, created.size());

        // created3
        Event created3 = created.get(0);
        assertTrue(isValidUUID(created3.getId()));
        toCreate3.setId(created3.getId());
        assertEquals(created3, toCreate3);
        // created4
        Event created4 = created.get(0);
        assertTrue(isValidUUID(created4.getId()));
        toCreate4.setId(created4.getId());
        assertEquals(created4, toCreate3);
    }

    @Test
    void student_write_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        AttendingApi api = new AttendingApi(student1Client);

        assertThrowsApiException(
                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
                () -> api.saveOrUpdateEvents(PLACE1_ID, List.of()));
    }

    @Test
    void teacher_write_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        AttendingApi api = new AttendingApi(teacher1Client);

        List<Event> toUpdate = api.saveOrUpdateEvents(PLACE1_ID,List.of(
                someCreatableEvent(),
                someCreatableEvent()));
        Event toUpdate0 = toUpdate.get(0);
        toUpdate0.setName("A new name zero");
        Event toUpdate1 = toUpdate.get(1);
        toUpdate1.setName("A new name one");

        List<Event> updated = api.saveOrUpdateEvents(PLACE1_ID,toUpdate);

        assertEquals(2, updated.size());
        assertTrue(updated.contains(toUpdate0));
        assertTrue(updated.contains(toUpdate1));
    }

    @Test
    void manager_write_update_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        AttendingApi api = new AttendingApi(manager1Client);
        List<Event> toUpdate = api.saveOrUpdateEvents(PLACE1_ID,List.of(
                someCreatableEvent(),
                someCreatableEvent()));
        Event toUpdate0 = toUpdate.get(0);
        toUpdate0.setName("prog");
        Event toUpdate1 = toUpdate.get(1);
        toUpdate1.setName("sys");

        List<Event> updated = api.saveOrUpdateEvents(PLACE1_ID,toUpdate);

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
