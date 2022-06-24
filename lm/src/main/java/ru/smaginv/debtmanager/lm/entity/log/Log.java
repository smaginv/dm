package ru.smaginv.debtmanager.lm.entity.log;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.http.HttpMethod;
import ru.smaginv.debtmanager.lm.util.PostgreSQLEnumType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "log")
@TypeDefs({
        @TypeDef(
                name = "pgsql_enum",
                typeClass = PostgreSQLEnumType.class
        ),
        @TypeDef(
                name = "jsonb",
                typeClass = JsonBinaryType.class
        )
})
public class Log {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "log-generator"
    )
    @SequenceGenerator(
            name = "log-generator",
            sequenceName = "log_seq",
            allocationSize = 10
    )
    @Column(name = "log_id")
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "username")
    private String username;

    @Column(name = "request_uri")
    private String requestURI;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private HttpMethod method;

    @Type(type = "jsonb")
    @Column(name = "request_body", columnDefinition = "jsonb")
    private Object requestBody;

    @Type(type = "jsonb")
    @Column(name = "response_body", columnDefinition = "jsonb")
    private Object responseBody;
}
