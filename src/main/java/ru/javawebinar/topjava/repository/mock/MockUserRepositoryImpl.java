package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class MockUserRepositoryImpl implements UserRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockUserRepositoryImpl.class);
    List<User> users = Arrays.asList(
            new User(0, "User1", "email1@mail.com", "pass1", Role.ROLE_USER),
            new User(1, "User2", "email2@mail.com", "pass2", Role.ROLE_USER),
            new User(2, "Admin", "email3@mail.com", "pass3", Role.ROLE_ADMIN)
    );

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return null;
    }
}
