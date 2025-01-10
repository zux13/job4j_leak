package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CommentGenerator implements Generate {

    private static final String PATH_PHRASES = "files/phrases.txt";
    private static final String SEPARATOR = System.lineSeparator();
    private static final int COUNT = 50;

    private final List<Comment> comments = new ArrayList<>();
    private final UserGenerator userGenerator;
    private final Random random;
    private List<String> phrases;

    public CommentGenerator(Random random, UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
        this.random = random;
    }

    private void initializePhrases() {
        try {
            phrases = read(PATH_PHRASES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void generate() {
        comments.clear();
        if (Objects.isNull(phrases)) {
            initializePhrases();
        }
        for (int i = 0; i < COUNT; i++) {
            String text = String.format("%s%s%s%s%s",
                    phrases.get(random.nextInt(phrases.size())), SEPARATOR,
                    phrases.get(random.nextInt(phrases.size())), SEPARATOR,
                    phrases.get(random.nextInt(phrases.size())));
            var comment = new Comment();
            comment.setText(text);
            comment.setUser(userGenerator.generateSingleUser());
            comments.add(comment);
        }
    }
}