package ru.smaginv.debtmanager.entity.user;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.smaginv.debtmanager.entity.HasId;
import ru.smaginv.debtmanager.util.entity.PostgreSQLEnumType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class User implements HasId {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users-generator"
    )
    @SequenceGenerator(
            name = "users-generator",
            sequenceName = "users_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<Role> roles;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
