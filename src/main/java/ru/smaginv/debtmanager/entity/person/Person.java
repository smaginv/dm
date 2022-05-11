package ru.smaginv.debtmanager.entity.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"email", "comment", "accounts"})
@Entity
@Table(name = "person")
public class Person {

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

    @NotBlank
    @Size(min = 2, max = 32)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 32)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(min = 4, max = 32)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @Size(max = 512)
    @Column(name = "comment")
    private String comment;

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Account> accounts;
}
