package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class UserGenerator implements Generate {

    private static final String PATH_NAMES = "files/names.txt";
    private static final String PATH_SURNAMES = "files/surnames.txt";
    private static final String PATH_PATRONS = "files/patr.txt";
    private static final int USER_COUNT = 1000;

    private final List<User> users = new ArrayList<>();
    private final Random random;
    private List<String> names;
    private List<String> surnames;
    private List<String> patrons;

    public UserGenerator(Random random) {
        this.random = random;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void generate() {
        if (Objects.isNull(names) || Objects.isNull(surnames) || Objects.isNull(patrons)) {
            readAll();
        }
        users.clear();
        for (int i = 0; i < USER_COUNT; i++) {
            var name = String.format("%s %s %s",
                    surnames.get(random.nextInt(surnames.size())),
                    names.get(random.nextInt(names.size())),
                    patrons.get(random.nextInt(patrons.size())));
            var user = new User();
            user.setName(name);
            users.add(user);
        }
    }

    private void readAll() {
        try {
            names = read(PATH_NAMES);
            surnames = read(PATH_SURNAMES);
            patrons = read(PATH_PATRONS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User randomUser() {
        if (users.isEmpty()) {
            generate();
        }
        return users.get(random.nextInt(users.size()));
    }

    public User generateSingleUser() {
        if (Objects.isNull(names) || Objects.isNull(surnames) || Objects.isNull(patrons)) {
            readAll();
        }
        var name = String.format("%s %s %s",
                surnames.get(random.nextInt(surnames.size())),
                names.get(random.nextInt(names.size())),
                patrons.get(random.nextInt(patrons.size())));
        var user = new User();
        user.setName(name);
        return user;
    }
}