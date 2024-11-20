package projects.f5.toys_java.dtos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToyDTOTest {

    private ToyDTO toyDTO;

    @BeforeEach
    void setUp() {
        String title = "Super Car";
        String brand = "ToyBrand";
        int recommendedAge = 5;
        String category = "Vehicles";
        String content = "Made of plastic, no sharp edges";
        int childType = 1;

        toyDTO = new ToyDTO(title, brand, recommendedAge, category, content, childType);
    }

    @Test
    void testGetTitle() {
        assertThat(toyDTO.getTitle(), is(equalTo("Super Car")));
    }

    @Test
    void testGetBrand() {
        assertThat(toyDTO.getBrand(), is(equalTo("ToyBrand")));
    }

    @Test
    void testGetRecommendedAge() {
        assertThat(toyDTO.getRecommendedAge(), is(equalTo(5)));
    }

    @Test
    void testGetCategory() {
        assertThat(toyDTO.getCategory(), is(equalTo("Vehicles")));
    }

    @Test
    void testGetContent() {
        assertThat(toyDTO.getContent(), is(equalTo("Made of plastic, no sharp edges")));
    }

    @Test
    void testGetChildType() {
        assertThat(toyDTO.getChildType(), is(equalTo(1)));
    }

}
