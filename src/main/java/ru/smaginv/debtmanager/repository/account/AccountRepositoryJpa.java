package ru.smaginv.debtmanager.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryJpa extends JpaRepository<Account, Long> {

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.id = :accountId AND p.user.id = :userId
            """)
    Optional<Account> get(@Param("accountId") Long accountId, @Param("userId") Long userId);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAll(@Param("userId") Long userId);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE p.id = :personId AND p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAllByPerson(@Param("personId") Long personId, @Param("userId") Long userId);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = :accountStatus AND p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAllByStatus(@Param("accountStatus") AccountStatus accountStatus,
                                 @Param("userId") Long userId);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountType = :accountType AND p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAllByType(@Param("accountType") AccountType accountType,
                               @Param("userId") Long userId);

    @Query("""
            SELECT SUM(a.amount) FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.ACTIVE AND
            a.accountType = :accountType AND p.user.id = :userId
            """)
    BigDecimal getActiveAccountsTotalAmountByType(@Param("accountType") AccountType accountType,
                                                  @Param("userId") Long userId);

    @Query("""
            SELECT SUM(a.amount) FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.accountType = :accountType AND p.user.id = :userId
            """)
    BigDecimal getInactiveAccountsTotalAmountByType(@Param("accountType") AccountType accountType,
                                                    @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.id = :accountId AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int delete(@Param("accountId") Long accountId, @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.person.id = :personId AND
            a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int deleteAllInactiveByPerson(@Param("personId") Long personId, @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int deleteAllInactive(@Param("userId") Long userId);
}
