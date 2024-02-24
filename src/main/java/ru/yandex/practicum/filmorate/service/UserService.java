package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(int id, int friendId) {
        User user = storage.getById(id);
        User friend = storage.getById(friendId);

        user.addFriend(friendId);
        friend.addFriend(id);
    }

    public void deleteFriend(int id, int friendId) {
        User user = storage.getById(id);
        User friend = storage.getById(friendId);

        user.deleteFriend(friendId);
        friend.deleteFriend(id);
    }

    public ArrayList<User> getCommonFriends(int id, int otherId) {
        User user1 = storage.getById(id);
        User user2 = storage.getById(otherId);

        Set<Integer> common = new HashSet<>(user1.getFriends());
        common.retainAll(user2.getFriends());

        ArrayList<User> commonFriends = new ArrayList<>();
        for (int friendId : common) {
            commonFriends.add(storage.getById(friendId));
        }

        return commonFriends;
    }

    public ArrayList<User> getFriends(int id) {
        ArrayList<User> friends = new ArrayList<>();
        User user = storage.getById(id);

        for (int friendId : user.getFriends()) {
            friends.add(storage.getById(friendId));
        }
        return friends;
    }

    public User create(User user) {
        return storage.create(user);
    }

    public User update(User user) {
        return storage.update(user);
    }

    public List<User> getAll() {
        return storage.getAll();
    }

    public User getById(int id) {
        return storage.getById(id);
    }
}
