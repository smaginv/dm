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

    @Query("SELECT o FROM Operation o WHERE o.id = :operationId AND o.account.id = :accountId")
    Optional<Operation> get(@Param("accountId") Long accountId, @Param("operationId") Long operationId);

    @Query("SELECT o FROM Operation o WHERE o.account.id = :accountId ORDER BY o.operDate DESC")
    List<Operation> getAllByAccount(@Param("accountId") Long accountId);

    @Query("SELECT o FROM Operation o ORDER BY o.operDate DESC")
    List<Operation> getAll();

    @Query("SELECT o FROM Operation o WHERE o.operationType = :operationType")
    List<Operation> getByType(@Param("operationType") OperationType operationType);

    @Query("SELECT o FROM Operation o WHERE o.operationType = :operationType AND o.account.id = :accountId")
    List<Operation> getByAccountAndType(@Param("accountId") Long accountId,
                                        @Param("operationType") OperationType operationType);

    @Query("""
            SELECT DISTINCT o FROM Operation o WHERE o.operationType = :operationType AND
             (:accountId IS NULL OR o.account.id = :accountId) AND
              o.operDate >= :startDateTime AND o.operDate <= :endDateTime
            """)
    List<Operation> find(@Param("accountId") Long accountId,
                         @Param("operationType") OperationType operationType,
                         @Param("startDateTime") LocalDateTime startDateTime,
                         @Param("endDateTime") LocalDateTime endDateTime);

    @Modifying
    @Query("DELETE FROM Operation o WHERE o.id = :operationId AND o.account.id = :accountId")
    int delete(@Param("accountId") Long accountId, @Param("operationId") Long operationId);

    @Modifying
    @Query("DELETE FROM Operation o WHERE o.account.id = :accountId")
    int deleteAllByAccount(@Param("accountId") Long accountId);
}
