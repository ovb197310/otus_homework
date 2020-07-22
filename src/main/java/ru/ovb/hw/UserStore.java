package ru.ovb.hw;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserStore implements IUserStore {

    private Map<Long, User> store = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong();

    @Override
    public User find(Long userId) {
        return store.get(userId);
    }

    @Override
    public Long save(User user) {
        user.setId(idGenerator.incrementAndGet());
        store.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public User delete(Long userId) {
        return store.remove(userId);
    }

    @Override
    public User update(User user) {
        User userUp = store.get(user.getId());
        if (userUp == null) {
            throw new IllegalArgumentException("Указан некорректный идентификатор пользователя: " + user.getId());
        }
        return store.put(user.getId(), user);
    }
}
