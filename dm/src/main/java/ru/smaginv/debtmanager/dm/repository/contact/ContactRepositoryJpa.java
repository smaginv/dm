package ru.smaginv.debtmanager.dm.repository.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.dm.entity.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepositoryJpa extends JpaRepository<Contact, Long> {

    @Query("""
            SELECT c FROM Contact c
            JOIN Person p ON c.person.id = p.id
            WHERE c.id = :contactId AND p.user.id = :userId
            """)
    Optional<Contact> get(@Param("userId") Long userId,
                          @Param("contactId") Long contactId);

    @Query("""
            SELECT c FROM Contact c
            JOIN Person p ON c.person.id = p.id
            WHERE p.id = :personId AND p.user.id = :userId
            ORDER BY c.id DESC
            """)
    List<Contact> getAllByPerson(@Param("userId") Long userId,
                                 @Param("personId") Long personId);

    @Query("""
            SELECT c FROM Contact c
            JOIN Person p ON c.person.id = p.id
            WHERE p.user.id = :userId
            ORDER BY c.id DESC
            """)
    List<Contact> getAll(@Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM Contact c
            WHERE c.id = :contactId AND
            c.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int delete(@Param("userId") Long userId,
               @Param("contactId") Long contactId);

    @Modifying
    @Query("""
            DELETE FROM Contact c
            WHERE c.person.id = :personId AND
            c.person.id IN (SELECT p.id FROM Person p WHERE p.user.id = :userId)
            """)
    int deleteAllByPerson(@Param("userId") Long userId,
                          @Param("personId") Long personId);
}
