package ru.smaginv.debtmanager.repository.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryJpa extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.id = :accountId AND a.person.id = :personId")
    Optional<Account> get(@Param("accountId") Long accountId, @Param("personId") Long personId);

    @EntityGraph(value = "account-operations")
    default Optional<Account> getWithOperations(@Param("accountId") Long accountId, @Param("personId") Long personId) {
        return get(accountId, personId);
    }

    @Query("SELECT a FROM Account a ORDER BY a.id")
    List<Account> getAll();

    @EntityGraph(value = "account-operations")
    default List<Account> getAllWithOperations() {
        return getAll();
    }

    @Query("SELECT a FROM Account a WHERE a.person.id = :personId")
    List<Account> getAllByPerson(@Param("personId") Long personId);

    @Query("SELECT a FROM Account a WHERE a.isActive = TRUE")
    List<Account> getAllActive();

    @Query("SELECT a FROM Account a WHERE a.person.id = :personId AND a.isActive = TRUE")
    List<Account> getAllActiveByPerson(@Param("personId") Long personId);

    @Query("SELECT a FROM Account a WHERE a.isActive = FALSE")
    List<Account> getAllInactive();

    @Query("SELECT a FROM Account a WHERE a.person.id = :personId AND a.isActive = FALSE")
    List<Account> getAllInactiveByPerson(@Param("personId") Long personId);

    @Query("SELECT a FROM Account a WHERE a.accountType = 'DEBIT'")
    List<Account> getAllDebit();

    @Query("SELECT a FROM Account a WHERE a.accountType = 'CREDIT'")
    List<Account> getAllCredit();

    @Modifying
    @Query("DELETE FROM Account a WHERE a.id = :accountId AND a.person.id = :personId")
    int delete(@Param("accountId") Long accountId, @Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.person.id = :personId")
    int deleteAllByPerson(@Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.person.id = :personId AND a.isActive = FALSE")
    int deleteAllInactiveByPerson(@Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.isActive = FALSE")
    int deleteAllInactive();
}
