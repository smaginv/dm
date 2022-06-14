package ru.smaginv.debtmanager.dm.entity.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "unique_contact")
public class UniqueContact {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unique-contact-generator"
    )
    @SequenceGenerator(
            name = "unique-contact-generator",
            sequenceName = "unique_contact_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "unique_contact_id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "contact_value")
    private String contactValue;
}
