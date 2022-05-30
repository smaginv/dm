package ru.smaginv.debtmanager.repository.user;

import ru.smaginv.debtmanager.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> get(Long userId);

    Optional<User> getByEmail(String email);

    List<User> getAll();

    User save(User user);

    int deleteByEmail(Long userId);

    int deleteByEmail(String email);
}
