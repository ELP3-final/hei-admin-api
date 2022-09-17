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
import school.hei.haapi.endpoint.rest.model.EventParticipant;
import school.hei.haapi.endpoint.rest.model.User;
import school.hei.haapi.endpoint.rest.model.CreateOrUpdateEventParticipant;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;
import static school.hei.haapi.integration.conf.TestUtils.FEE3_ID;

import school.hei.haapi.endpoint.rest.model.EnableStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = EventParticipantIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class EventParticipantIT {
  @MockBean private SentryConf sentryConf;
  @MockBean private CognitoComponent cognitoComponentMock;

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailableRandomPort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }

  private static ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, EventParticipantIT.ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpCognito(cognitoComponentMock);
  }

  static EventParticipant eventParticipant1(){
    User user = new User();
    user.setId("student1_id");
    user.setFirstName("Ryan");
    user.setLastName("Andria");
    user.setEmail("test+ryan@hei.school");
    user.setRef("STD21001");
    user.setPhone("0322411123");
    user.setStatus(EnableStatus.ENABLED);
    user.setSex(User.SexEnum.M);
    user.setBirthDate(LocalDate.parse("2000-01-01"));
    user.setEntranceDatetime(Instant.parse("2021-11-08T08:25:24.00Z"));
    user.setAddress("Adr 1");
    return new EventParticipant().
          id(EVENTPARTICIPANT1_ID)
          .attendance(EventParticipant.AttendanceEnum.EXPECTED)
          .eventRole(EventParticipant.EventRoleEnum.ATTENDANT)
          .user(user);
  }
  static EventParticipant eventParticipant2(){
    User teacher = new User();
    teacher.setId("teacher1_id");
    teacher.setFirstName("One");
    teacher.setLastName("Teacher");
    teacher.setEmail("test+teacher1@hei.school");
    teacher.setRef("TCR21001");
    teacher.setPhone("0322411125");
    teacher.setStatus(EnableStatus.ENABLED);
    teacher.setSex(User.SexEnum.F);
    teacher.setBirthDate(LocalDate.parse("1990-01-01"));
    teacher.setEntranceDatetime(Instant.parse("2021-10-08T08:27:24.00Z"));
    teacher.setAddress("Adr 3");
    return new EventParticipant().
          id(EVENTPARTICIPANT2_ID)
          .attendance(EventParticipant.AttendanceEnum.EXPECTED)
          .eventRole(EventParticipant.EventRoleEnum.SUPERVISOR)
          .user(teacher);
  }

  static CreateOrUpdateEventParticipant creatableEventParticipant1() {
    return new CreateOrUpdateEventParticipant()
            .attendance(CreateOrUpdateEventParticipant.AttendanceEnum.EXPECTED)
            .eventRole(CreateOrUpdateEventParticipant.EventRoleEnum.ATTENDANT)
            .userId(STUDENT2_ID);
  }
  static CreateOrUpdateEventParticipant creatableEventParticipant2() {
    return new CreateOrUpdateEventParticipant()
            .id(EVENTPARTICIPANT1_ID)
            .attendance(CreateOrUpdateEventParticipant.AttendanceEnum.HERE);
  }
  @Test
  void student_read_ko() {
    ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
    AttendingApi api = new AttendingApi(student1Client);

    assertThrowsApiException(
            "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
            () -> api.getEventParticipants(EVENT1_ID));
  }

  @Test
  void teacher_read_ok() throws ApiException {
    ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
    AttendingApi api = new AttendingApi(teacher1Client);
    List<EventParticipant> participants = api.getEventParticipants(EVENT1_ID);
    assertTrue(participants.contains(eventParticipant1()));
    assertTrue(participants.contains(eventParticipant2()));
  }


  @Test
  void manager_read_ok() throws ApiException {
    ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);

    AttendingApi api = new AttendingApi(manager1Client);
    List<EventParticipant> participants = api.getEventParticipants(EVENT1_ID);
    assertTrue(participants.contains(eventParticipant1()));
    assertTrue(participants.contains(eventParticipant2()));
  }

  @Test
  void student_write_ko(){
    ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
    AttendingApi api = new AttendingApi(student1Client);
    List<CreateOrUpdateEventParticipant> toAdd = new ArrayList<CreateOrUpdateEventParticipant>();
    toAdd.add(creatableEventParticipant1());
    assertThrowsApiException(
            "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
            () -> api.createOrUpdateEventParticipants(EVENT1_ID,toAdd));
  }

  @Test
  void manager_write_ok() throws ApiException {
    ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
    AttendingApi api = new AttendingApi(manager1Client);
    List<CreateOrUpdateEventParticipant> toAdd = new ArrayList<CreateOrUpdateEventParticipant>();
    toAdd.add(creatableEventParticipant1());
    List<EventParticipant> news = api.createOrUpdateEventParticipants(EVENT1_ID,toAdd);
    List<EventParticipant> participants = api.getEventParticipants(EVENT1_ID);
    assertTrue(participants.containsAll(news));
  }

}
