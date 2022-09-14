package school.hei.haapi.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.repository.types.PostgresEnumType;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"event_participant\"")
@Getter
@Setter
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipant implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Type(type = "pgsql_enum")
  @Enumerated(EnumType.STRING)
  private school.hei.haapi.endpoint.rest.model.EventParticipant.EventRoleEnum eventRole;

  @Type(type = "pgsql_enum")
  @Enumerated(EnumType.STRING)
  private school.hei.haapi.endpoint.rest.model.EventParticipant.AttendanceEnum attendance;

}