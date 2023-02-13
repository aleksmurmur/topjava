package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> map = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    {
        save(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
        save(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return map.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(id.incrementAndGet());
            map.put(user.getId(), user);
            return user;
        }
        return map.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return map.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> result = new ArrayList<>(map.values());
        result.sort(Comparator.comparing(User::getName));
         return result;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Optional<User> opt = map.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();


        return opt.orElse(null);
    }
}
