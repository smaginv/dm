package ru.smaginv.debtmanager.repository.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.contact.UniqueContact;

import java.util.List;

@Repository
public interface UniqueContactRepository extends JpaRepository<UniqueContact, Long> {

    @Modifying
    @Query("""
            DELETE FROM UniqueContact uc
            WHERE uc.contactId = :contactId AND
            uc.userId = :userId
            """)
    void deleteByContactId(@Param("contactId") Long contactId, @Param("userId") Long userId);

    @Modifying
    @Query("""
            DELETE FROM UniqueContact uc
            WHERE uc.contactId IN :contactIds AND
            uc.userId = :userId
            """)
    void deleteByContactIds(@Param("contactIds") List<Long> contactIds, @Param("userId") Long userId);
}
