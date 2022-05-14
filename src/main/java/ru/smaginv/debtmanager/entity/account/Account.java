package ru.smaginv.debtmanager.entity.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.util.entity.PostgreSQLEnumType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"closedDate", "comment"})
@Entity
@Table(name = "account")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@NamedEntityGraph(
        name = "account-operations",
        attributeNodes = {
                @NamedAttributeNode("operations")
        }
)
public class Account {

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

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "1.0")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currencyCode;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "99.9")
    @Column(name = "rate")
    private Float rate;

    @NotNull
    @Column(name = "open_date")
    private LocalDateTime openDate;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Size(max = 512)
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @OneToMany(
            mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Operation> operations;
}
