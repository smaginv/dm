package ru.smaginv.debtmanager.repository.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.operation.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepositoryJpa extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o WHERE o.id = :operationId AND o.account.id = :accountId")
    Operation get(@Param("operationId") Long operationId, @Param("accountId") Long accountId);

    @Query("SELECT o FROM Operation o WHERE o.account.id = :accountId")
    List<Operation> getAllByAccount(@Param("accountId") Long accountId);

    @Query("SELECT o FROM Operation o ORDER BY o.id")
    List<Operation> getAll();

    @Query("SELECT o FROM Operation o WHERE o.operationType = 'LEND'")
    List<Operation> getAllLend();

    @Query("SELECT o FROM Operation o WHERE o.account.id = :accountId AND o.operationType = 'LEND'")
    List<Operation> getAllLendByAccount(@Param("accountId") Long accountId);

    @Query("SELECT o FROM Operation o WHERE o.operationType = 'LOAN'")
    List<Operation> getAllLoan();

    @Query("SELECT o FROM Operation o WHERE o.account.id = :accountId AND o.operationType = 'LOAN'")
    List<Operation> getAllLoanByAccount(@Param("accountId") Long accountId);

    @Query("SELECT o FROM Operation o WHERE o.operDate >= :startDate AND o.operDate <= :endDate ORDER BY o.operDate")
    List<Operation> getBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT o FROM Operation o WHERE o.account.id = :accountId AND
             o.operDate >= :startDate AND o.operDate <= :endDate
            """)
    List<Operation> getBetweenDatesByAccount(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate,
                                             @Param("accountId") Long accountId);

    @Query("""
            SELECT o FROM Operation o WHERE o.operationType = 'LEND' AND
             o.operDate >= :startDate AND o.operDate <= :endDate
            """)
    List<Operation> getAllLendBetweenDates(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT o FROM Operation o WHERE o.operationType = 'LOAN' AND
             o.operDate >= :startDate AND o.operDate <= :endDate
            """)
    List<Operation> getAllLoanBetweenDates(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT o FROM Operation o WHERE o.account.id = :accountId AND o.operationType = 'LEND' AND
             o.operDate >= :startDate AND o.operDate <= :endDate
            """)
    List<Operation> getAllLendBetweenDatesByAccount(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("accountId") Long accountId);

    @Query("""
            SELECT o FROM Operation o WHERE o.account.id = :accountId AND o.operationType = 'LOAN' AND
             o.operDate >= :startDate AND o.operDate <= :endDate
            """)
    List<Operation> getAllLoanBetweenDatesByAccount(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Operation o WHERE o.id = :operationId AND o.account.id = :accountId")
    int delete(@Param("operationId") Long operationId, @Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Operation o WHERE o.account.id = :accountId")
    int deleteAllByAccount(@Param("accountId") Long accountId);
}
