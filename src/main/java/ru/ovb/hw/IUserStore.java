package ru.ovb.hw;

public interface IUserStore {

    User find(Long userId);
    Long save(User user);
    User delete(Long userId);
    User update(User user);
}
