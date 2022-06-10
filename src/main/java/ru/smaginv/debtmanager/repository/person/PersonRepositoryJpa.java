package ru.smaginv.debtmanager.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryJpa extends JpaRepository<Person, Long> {

    @Query("""
            SELECT p FROM Person p
            WHERE p.id = :personId AND p.user.id = :userId
            """)
    Optional<Person> get(@Param("personId") Long personId, @Param("userId") Long userId);

    @Query("""
            SELECT p FROM Person p
            JOIN Contact c ON p.id = c.person.id
            WHERE c.contactType = :#{#contact.contactType} AND c.value = :#{#contact.value} AND
            p.user.id = :userId
            """)
    Optional<Person> getByContact(@Param("contact") Contact contact, @Param("userId") Long userId);

    @Query("""
            SELECT p FROM Person p
            WHERE p.user.id = :userId
            ORDER BY p.id
            """)
    List<Person> getAll(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT p FROM Person p
            WHERE p.user.id = :userId AND
            (p.id = :#{#person.id} OR p.firstName = :#{#person.firstName} OR p.lastName = :#{#person.lastName})
            """)
    List<Person> find(@Param("person") Person person, @Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT p FROM Person p
            JOIN Contact c ON p.id = c.person.id
            WHERE p.user.id = :userId AND
            (p.id = :#{#person.id} OR p.firstName = :#{#person.firstName} OR p.lastName = :#{#person.lastName} OR
            (c.contactType = :#{#contact.contactType} AND c.value = :#{#contact.value}))
            """)
    List<Person> find(@Param("person") Person person,
                      @Param("contact") Contact contact,
                      @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Person p
            WHERE p.id = :personId AND p.user.id = :userId
            """)
    int delete(@Param("personId") Long personId, @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Person p
            WHERE p.user.id = :userId AND
            p.id IN
            (SELECT c.person.id FROM Contact c
            WHERE c.contactType = :#{#contact.contactType} AND c.value = :#{#contact.value})
            """)
    int deleteByContact(@Param("contact") Contact contact, @Param("userId") Long userId);
}
