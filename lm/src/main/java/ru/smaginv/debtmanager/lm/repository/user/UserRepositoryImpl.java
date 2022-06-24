package ru.smaginv.debtmanager.lm.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.lm.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepository;

    @Autowired
    public UserRepositoryImpl(UserRepositoryJpa userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public int deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public int deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }
}
