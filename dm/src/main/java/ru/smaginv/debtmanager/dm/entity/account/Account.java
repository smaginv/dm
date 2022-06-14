package ru.smaginv.debtmanager.dm.entity.account;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.smaginv.debtmanager.dm.entity.HasId;
import ru.smaginv.debtmanager.dm.entity.person.Person;
import ru.smaginv.debtmanager.dm.util.entity.PostgreSQLEnumType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"closedDate", "comment"})
@Entity
@Table(name = "account")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Account implements HasId {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account-generator"
    )
    @SequenceGenerator(
            name = "account-generator",
            sequenceName = "account_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "account_id")
    private Long id;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountType accountType;

    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.0")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currencyCode;

    @Digits(integer = 2, fraction = 2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "99.9")
    @Column(name = "rate")
    private Float rate;

    @PastOrPresent
    @Column(name = "open_date")
    private LocalDateTime openDate;

    @PastOrPresent
    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Size(max = 512)
    @Column(name = "comment")
    private String comment;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus accountStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
