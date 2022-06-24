package ru.smaginv.debtmanager.lm.repository.user;

import ru.smaginv.debtmanager.lm.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);

    List<User> getAll();

    User save(User user);

    int deleteByUsername(String username);

    int deleteByEmail(String email);
}
