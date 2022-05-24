package ru.smaginv.debtmanager.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryJpa extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.id = :accountId AND a.person.id = :personId")
    Optional<Account> get(@Param("personId") Long personId, @Param("accountId") Long accountId);

    @Query("SELECT a FROM Account a ORDER BY a.id")
    List<Account> getAll();

    @Query("SELECT a FROM Account a WHERE a.person.id = :personId ORDER BY a.amount")
    List<Account> getAllByPerson(@Param("personId") Long personId);

    @Query("SELECT a FROM Account a WHERE a.active = :active")
    List<Account> getByState(@Param("active") boolean active);

    @Query("SELECT a FROM Account a WHERE a.person.id = :personId AND a.active = :active")
    List<Account> getByPersonAndState(@Param("personId") Long personId, @Param("active") boolean active);

    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType")
    List<Account> getAllByType(@Param("accountType") AccountType accountType);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.id = :accountId AND a.person.id = :personId")
    int delete(@Param("personId") Long personId, @Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.person.id = :personId AND a.active = :#{false}")
    int deleteAllInactiveByPerson(@Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.active = :#{false}")
    int deleteAllInactive();
}
