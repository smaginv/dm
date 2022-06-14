package ru.smaginv.debtmanager.repository.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OperationRepositoryJpa extends JpaRepository<Operation, Long> {

    @Query("""
            SELECT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE o.id = :operationId AND p.user.id = :userId
            """)
    Optional<Operation> get(@Param("userId") Long userId,
                            @Param("operationId") Long operationId);

    @Query("""
            SELECT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE a.id = :accountId AND p.user.id = :userId
            ORDER BY o.operDate DESC
            """)
    List<Operation> getAllByAccount(@Param("userId") Long userId,
                                    @Param("accountId") Long accountId);

    @Query("""
            SELECT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE p.user.id = :userId
            ORDER BY o.operDate DESC
            """)
    List<Operation> getAll(@Param("userId") Long userId);

    @Query("""
            SELECT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE o.operationType = :operationType AND
            p.user.id = :userId
            ORDER BY o.operDate DESC
            """)
    List<Operation> getByType(@Param("userId") Long userId,
                              @Param("operationType") OperationType operationType);

    @Query("""
            SELECT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE a.id = :accountId AND o.operationType = :operationType AND
            p.user.id = :userId
            ORDER BY o.operDate DESC
            """)
    List<Operation> getByAccountAndType(@Param("userId") Long userId,
                                        @Param("accountId") Long accountId,
                                        @Param("operationType") OperationType operationType);

    @Query("""
            SELECT DISTINCT o FROM Operation o
            JOIN Account a ON o.account.id = a.id
            JOIN Person p ON a.person.id = p.id
            WHERE o.operationType = :operationType AND
            (:accountId IS NULL OR a.id = :accountId) AND
            o.operDate >= :startDateTime AND o.operDate <= :endDateTime AND
            p.user.id = :userId
            ORDER BY o.operDate DESC
            """)
    List<Operation> find(@Param("userId") Long userId,
                         @Param("accountId") Long accountId,
                         @Param("operationType") OperationType operationType,
                         @Param("startDateTime") LocalDateTime startDateTime,
                         @Param("endDateTime") LocalDateTime endDateTime);

    @Modifying
    @Query("""
            DELETE FROM Operation o
            WHERE o.id = :operationId AND
            :userId IN (SELECT u.id FROM User u)
            """)
    int delete(@Param("userId") Long userId,
               @Param("operationId") Long operationId);

    @Modifying
    @Query("""
            DELETE FROM Operation o
            WHERE o.account.id = :accountId AND
            :userId IN (SELECT u.id FROM User u)
            """)
    int deleteAllByAccount(@Param("userId") Long userId,
                           @Param("accountId") Long accountId);
}
