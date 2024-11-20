package projects.f5.toys_java.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BadToyTest {

    private BadToy badToy;

    @BeforeEach
    void setUp() {
        String id = "1";
        String customId = "B1";
        String title = "Broken Robot";
        String content = "Contains sharp pieces";

        badToy = new BadToy(id, customId, title, content);
    }

    @Test
    void testConstructor() {
        assertThat(badToy.getId(), is(equalTo("1")));
        assertThat(badToy.getCustomId(), is(equalTo("B1")));
        assertThat(badToy.getTitle(), is(equalTo("Broken Robot")));
        assertThat(badToy.getContent(), is(equalTo("Contains sharp pieces")));
        assertThat(badToy.getChildType(), is(equalTo(2)));
    }

    @Test
    void testToString() {
        String result = badToy.toString();

        assertThat(result, containsString("B1"));
        assertThat(result, containsString("Broken Robot"));
        assertThat(result, containsString("Contains sharp pieces"));

    }

}
