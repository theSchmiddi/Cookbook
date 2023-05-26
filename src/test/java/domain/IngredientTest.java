package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    public void testGetName() {
        Ingredient ingredient = new Ingredient("Flour", 500, "g");

        String result = ingredient.getName();

        assertEquals("Flour", result);
    }

    @Test
    public void testGetAmount() {
        Ingredient ingredient = new Ingredient("Sugar", 200, "g");

        int result = ingredient.getAmount();

        assertEquals(200, result);
    }

    @Test
    public void testGetUnit() {
        Ingredient ingredient = new Ingredient("Eggs", 3, "");

        String result = ingredient.getUnit();

        assertEquals("", result);
    }

    @Test
    public void testToString() {
        Ingredient ingredient = new Ingredient("Butter", 100, "g");

        String result = ingredient.toString();

        assertEquals("Butter: 100 g", result);
    }
}
