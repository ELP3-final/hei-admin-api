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
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Place;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.BAD_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.MANAGER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.PLACE1_ID;
import static school.hei.haapi.integration.conf.TestUtils.PLACE2_ID;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TEACHER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsForbiddenException;
import static school.hei.haapi.integration.conf.TestUtils.isValidUUID;
import static school.hei.haapi.integration.conf.TestUtils.setUpCognito;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = PlaceIT.ContextInitializer.class)
@AutoConfigureMockMvc
class PlaceIT {
    @MockBean
    private SentryConf sentryConf;
    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, PlaceIT.ContextInitializer.SERVER_PORT);
    }

    static Place place1() {
        return new Place()
                .id(PLACE1_ID)
                .label("HEI Ivandry");
    }

    static Place place2() {
        return new Place()
                .id(PLACE2_ID)
                .label("AF Andavamamba");
    }

    public static Place someCreatablePlace() {
        Place place = new Place();
        place.setLabel("label-"+ randomUUID());
        return place;
    }

    @BeforeEach
    void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void badtoken_read_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        AttendingApi api = new AttendingApi(anonymousClient);
        assertThrowsForbiddenException(api::getPlaces);
    }

    @Test
    void badtoken_write_ko() {
        ApiClient anonymousClient = anApiClient(BAD_TOKEN);

        AttendingApi api = new AttendingApi(anonymousClient);
        assertThrowsForbiddenException(() -> api.saveOrUpdatePlaces(List.of()));
    }

    @Test
    void student_read_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        AttendingApi api = new AttendingApi(student1Client);

        Place actual1 = api.getPlaceById(PLACE1_ID);
        List<Place> actualPlaces = api.getPlaces();

        assertEquals(place1(), actual1);
        assertTrue(actualPlaces.contains(place1()));
        assertTrue(actualPlaces.contains(place2()));
    }

    @Test
    void manager_read_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        AttendingApi api = new AttendingApi(manager1Client);

        Place actual1 = api.getPlaceById(PLACE1_ID);
        List<Place> actualPlaces = api.getPlaces();

        assertEquals(place1(), actual1);
        assertTrue(actualPlaces.contains(place1()));
        assertTrue(actualPlaces.contains(place2()));
    }

    @Test
    void teacher_read_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        AttendingApi api = new AttendingApi(teacher1Client);

        Place actual1 = api.getPlaceById(PLACE1_ID);
        List<Place> actualPlaces = api.getPlaces();

        assertEquals(place1(), actual1);
        assertTrue(actualPlaces.contains(place1()));
        assertTrue(actualPlaces.contains(place2()));
    }

    @Test
    void manager_write_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        Place toCreate3 = someCreatablePlace();
        Place toCreate4 = someCreatablePlace();

        AttendingApi api = new AttendingApi(manager1Client);
        List<Place> created = api.saveOrUpdatePlaces(List.of(toCreate3, toCreate4));

        assertEquals(2, created.size());

        // created3
        Place created3 = created.get(0);
        assertTrue(isValidUUID(created3.getId()));
        toCreate3.setId(created3.getId());
        assertEquals(created3, toCreate3);
        // created4
        Place created4 = created.get(0);
        assertTrue(isValidUUID(created4.getId()));
        toCreate4.setId(created4.getId());
        assertEquals(created4, toCreate3);
    }

    @Test
    void student_write_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        AttendingApi api = new AttendingApi(student1Client);

        assertThrowsForbiddenException(() -> api.saveOrUpdatePlaces(List.of()));
    }

    @Test
    void teacher_write_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        AttendingApi api = new AttendingApi(teacher1Client);

        List<Place> toUpdate = api.saveOrUpdatePlaces(List.of(
                someCreatablePlace(),
                someCreatablePlace()));
        Place toUpdate0 = toUpdate.get(0);
        toUpdate0.setLabel("A new name zero");
        Place toUpdate1 = toUpdate.get(1);
        toUpdate1.setLabel("A new name one");

        List<Place> updated = api.saveOrUpdatePlaces(toUpdate);

        assertEquals(2, updated.size());
        assertTrue(updated.contains(toUpdate0));
        assertTrue(updated.contains(toUpdate1));
    }

    @Test
    void manager_write_update_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        AttendingApi api = new AttendingApi(manager1Client);
        List<Place> toUpdate = api.saveOrUpdatePlaces(List.of(
                someCreatablePlace(),
                someCreatablePlace()));
        Place toUpdate0 = toUpdate.get(0);
        toUpdate0.setLabel("Ambohimanarina");
        Place toUpdate1 = toUpdate.get(1);
        toUpdate1.setLabel("Analakely");

        List<Place> updated = api.saveOrUpdatePlaces(toUpdate);

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
