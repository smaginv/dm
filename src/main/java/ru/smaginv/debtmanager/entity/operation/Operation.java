package ru.smaginv.debtmanager.entity.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.util.entity.PostgreSQLEnumType;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"description", "account"})
@Entity
@Table(name = "operation")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Operation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "operation-generator"
    )
    @SequenceGenerator(
            name = "operation-generator",
            sequenceName = "operation_seq",
            initialValue = 20,
            allocationSize = 10
    )
    @Column(name = "operation_id")
    private Long id;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OperationType operationType;

    @NotNull
    @Column(name = "oper_date")
    private LocalDateTime operDate;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "1.0")
    @Column(name = "amount")
    private BigDecimal amount;

    @Size(max = 512)
    @Column(name = "description")
    private String description;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Account account;
}
