package ru.smaginv.debtmanager.dm.entity.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.HasId;
import ru.smaginv.debtmanager.dm.entity.user.User;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"comment"})
@Entity
@Table(name = "person")
public class Person implements HasId {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person-generator"
    )
    @SequenceGenerator(
            name = "person-generator",
            sequenceName = "person_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "person_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
