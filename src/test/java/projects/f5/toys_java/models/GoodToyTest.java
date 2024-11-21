package projects.f5.toys_java.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoodToyTest {

    private GoodToy goodToy;

    @BeforeEach
    void setUp() {
        String id = "1";
        String customId = "G1";
        String title = "Star Wars Ship";
        String brand = "Lego";
        int recommendedAge = 8;
        String category = "Constructor";

        goodToy = new GoodToy(id, customId, title, brand, recommendedAge, category);
    }

    @Test
    void testConstructor() {
        assertThat(goodToy.getId(), is(equalTo("1")));
        assertThat(goodToy.getCustomId(), is(equalTo("G1")));
        assertThat(goodToy.getTitle(), is(equalTo("Star Wars Ship")));
        assertThat(goodToy.getBrand(), is(equalTo("Lego")));
        assertThat(goodToy.getRecommendedAge(), is(equalTo(8)));
        assertThat(goodToy.getCategory(), is(equalTo("Constructor")));
        assertThat(goodToy.getChildType(), is(equalTo(1)));
    }

    @Test
    void testToString() {
        String result = goodToy.toString();

        assertThat(result, containsString("G1"));
        assertThat(result, containsString("Star Wars Ship"));
        assertThat(result, containsString("Lego"));
        assertThat(result, containsString("8"));
        assertThat(result, containsString("Constructor"));
    }
}
