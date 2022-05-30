package ru.smaginv.debtmanager.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> get(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u ORDER BY u.id")
    List<User> getAll();

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :userId")
    int delete(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM User u WHERE u.email = :email")
    int deleteByEmail(@Param("email") String email);
}
