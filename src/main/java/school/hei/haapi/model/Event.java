package school.hei.haapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.repository.types.PostgresEnumType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"event\"")
@Getter
@Setter
@ToString
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    private String name;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Transient
    private school.hei.haapi.endpoint.rest.model.Event.TypeEnum type;

    private LocalDate date;

    private String startTime;

    private String finishTime;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Transient
    private school.hei.haapi.endpoint.rest.model.Event.SupervisorEnum supervisor;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Transient
    private school.hei.haapi.endpoint.rest.model.Event.StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
