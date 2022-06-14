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
    Optional<Account> get(@Param("userId") Long userId,
                          @Param("accountId") Long accountId);

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
    List<Account> getAllByPerson(@Param("userId") Long userId,
                                 @Param("personId") Long personId);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = :accountStatus AND p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAllByStatus(@Param("userId") Long userId,
                                 @Param("accountStatus") AccountStatus accountStatus);

    @Query("""
            SELECT a FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountType = :accountType AND p.user.id = :userId
            ORDER BY a.openDate DESC
            """)
    List<Account> getAllByType(@Param("userId") Long userId,
                               @Param("accountType") AccountType accountType);

    @Query("""
            SELECT SUM(a.amount) FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.ACTIVE AND
            a.accountType = :accountType AND p.user.id = :userId
            """)
    BigDecimal getActiveAccountsTotalAmountByType(@Param("userId") Long userId,
                                                  @Param("accountType") AccountType accountType);

    @Query("""
            SELECT SUM(a.amount) FROM Account a
            JOIN Person p ON a.person.id = p.id
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.accountType = :accountType AND p.user.id = :userId
            """)
    BigDecimal getInactiveAccountsTotalAmountByType(@Param("userId") Long userId,
                                                    @Param("accountType") AccountType accountType);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.id = :accountId AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int delete(@Param("userId") Long userId,
               @Param("accountId") Long accountId);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.person.id = :personId AND
            a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int deleteAllInactiveByPerson(@Param("userId") Long userId,
                                  @Param("personId") Long personId);

    @Modifying
    @Query("""
            DELETE FROM Account a
            WHERE a.accountStatus = ru.smaginv.debtmanager.entity.account.AccountStatus.INACTIVE AND
            a.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int deleteAllInactive(@Param("userId") Long userId);
}
