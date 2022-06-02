package ru.smaginv.debtmanager.repository.user;

import ru.smaginv.debtmanager.entity.user.Status;
import ru.smaginv.debtmanager.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> get(Long userId);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);

    List<User> getAll();

    List<User> getAllByStatus(Status status);

    User save(User user);

    int deleteByEmail(Long userId);

    int deleteByEmail(String email);
}
