package ru.smaginv.debtmanager.repository.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepositoryJpa extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.id = :contactId AND c.person.id = :personId")
    Optional<Contact> get(@Param("contactId") Long contactId, @Param("personId") Long personId);

    @Query("SELECT c FROM Contact c WHERE c.person.id = :personId")
    List<Contact> getAllByPerson(@Param("personId") Long personId);

    @Query("SELECT c FROM Contact c ORDER BY c.id")
    List<Contact> getAll();

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.id = :contactId AND c.person.id = :personId")
    int delete(@Param("contactId") Long contactId, @Param("personId") Long personId);

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.person.id = :personId")
    int deleteAllByPerson(@Param("personId") Long personId);
}
