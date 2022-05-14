package ru.smaginv.debtmanager.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryJpa extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id = :personId")
    Optional<Person> get(@Param("personId") Long personId);

    @Query("SELECT p FROM Person p JOIN Contact c WHERE c.contactType = 'PHONE' AND c.value = :phoneNumber")
    Optional<Person> getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT p FROM Person p JOIN Contact c WHERE c.contactType = 'PHONE' AND c.value IN :phoneNumbers")
    List<Person> getByPhoneNumbers(@Param("phoneNumbers") String... phoneNumbers);

    @Query("SELECT p FROM Person p JOIN Contact c WHERE c.contactType = 'EMAIL' AND c.value = :email")
    Optional<Person> getByEmail(@Param("email") String email);

    @Query("SELECT p FROM Person p JOIN Contact c WHERE c.contactType = 'EMAIL' AND c.value IN :emails")
    List<Person> getByEmails(@Param("emails") String... emails);

    @Query("SELECT p FROM Person p ORDER BY p.id")
    List<Person> getAll();

    @Modifying
    @Query("DELETE FROM Person p WHERE p.id = :personId")
    int delete(@Param("personId") Long personId);

    @Modifying
    @Query("""
            DELETE FROM Person p WHERE p.id IN
             (SELECT c.person.id FROM Contact c WHERE c.contactType = 'PHONE' AND c.value = :phoneNumber)
            """)
    int deleteByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("""
            DELETE FROM Person p WHERE p.id IN
             (SELECT c.person.id FROM Contact c WHERE c.contactType = 'EMAIL' AND c.value = :email)
            """)
    int deleteByEmail(@Param("email") String email);
}
