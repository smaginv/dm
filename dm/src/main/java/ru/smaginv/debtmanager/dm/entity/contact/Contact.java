package ru.smaginv.debtmanager.dm.entity.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.smaginv.debtmanager.dm.entity.HasId;
import ru.smaginv.debtmanager.dm.entity.person.Person;
import ru.smaginv.debtmanager.dm.util.entity.PostgreSQLEnumType;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contact")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Contact implements HasId {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contact-generator"
    )
    @SequenceGenerator(
            name = "contact-generator",
            sequenceName = "contact_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "contact_id")
    private Long id;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContactType contactType;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
