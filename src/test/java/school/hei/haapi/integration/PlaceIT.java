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
        return TestUtils.anApiClient(token, PaymentIT.ContextInitializer.SERVER_PORT);
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

    }

    @Test
    void teacher_write_ko() {
    }

    @Test
    void student_write_ko() {
    }

    @Test
    void manager_write_ko() throws ApiException {
    }

    @Test
    void manager_write_with_some_bad_fields_ko() {
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
