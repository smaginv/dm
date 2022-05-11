package ru.smaginv.debtmanager.repository.person;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;

public interface PersonRepositoryJpa extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id = :personId")
    Person get(@Param("personId") Long personId);

    @EntityGraph(value = "person-accounts")
    @Query("SELECT p FROM Person p WHERE p.id = :personId")
    Person getWithAccounts(@Param("personId") Long personId);

    @Query("SELECT p FROM Person p WHERE p.phoneNumber = : phoneNumber")
    Person getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @EntityGraph(value = "person-accounts")
    @Query("SELECT p FROM Person p WHERE p.phoneNumber = :phoneNumber")
    Person getWithAccountsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT p FROM Person p WHERE p.phoneNumber IN :phoneNumbers")
    List<Person> getByPhoneNumbers(@Param("phoneNumbers") String... phoneNumbers);

    @Query("SELECT p FROM Person p WHERE p.email = :email")
    Person getByEmail(@Param("email") String email);

    @EntityGraph(value = "person-accounts")
    @Query("SELECT p FROM Person p WHERE p.email = :email")
    Person getWithAccountsByEmail(@Param("email") String email);

    @Query("SELECT p FROM Person p WHERE p.email IN :emails")
    List<Person> getByEmails(@Param("emails") String... emails);

    @Query("SELECT p FROM Person p ORDER BY p.id")
    List<Person> getAll();

    @Modifying
    @Query("DELETE FROM Person p WHERE p.id = :personId")
    int delete(@Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Person p where p.phoneNumber = :phoneNumber")
    int deleteByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("DELETE FROM Person p WHERE p.email = :email")
    int deleteByEmail(@Param("email") String email);
}
