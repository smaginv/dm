package ru.smaginv.debtmanager.dm.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.dm.entity.user.Status;
import ru.smaginv.debtmanager.dm.entity.user.User;

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
    public Optional<User> get(Long userId) {
        return userRepository.get(userId);
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
    public List<User> getAllByStatus(Status status) {
        return userRepository.getAllByStatus(status);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public int deleteByEmail(Long userId) {
        return userRepository.delete(userId);
    }

    @Override
    public int deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }
}
