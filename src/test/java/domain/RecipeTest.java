package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeTest {

    @Test
    public void testGetIngredients() {
        Ingredient ingredient1 = new Ingredient("Flour", 500, "g");
        Ingredient ingredient2 = new Ingredient("Sugar", 200, "g");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        Recipe recipe = new Recipe(1, "Cake", ingredients, "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        List<Ingredient> result = recipe.getIngredients();

        assertEquals(ingredients, result);
    }

    @Test
    public void testGetPreparation() {
        Recipe recipe = new Recipe(1, "Cake", new ArrayList<>(), "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        String result = recipe.getPreparation();

        assertEquals("Mix flour and sugar. Bake in oven.", result);
    }

    @Test
    public void testGetPreparationTime() {
        Recipe recipe = new Recipe(1, "Cake", new ArrayList<>(), "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        int result = recipe.getPreparationTime();

        assertEquals(60, result);
    }

    @Test
    public void testGetServings() {
        Recipe recipe = new Recipe(1, "Cake", new ArrayList<>(), "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        int result = recipe.getServings();

        assertEquals(8, result);
    }

    @Test
    public void testGetNotes() {
        Recipe recipe = new Recipe(1, "Cake", new ArrayList<>(), "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        String result = recipe.getNotes();

        assertEquals("Best served with whipped cream.", result);
    }

    @Test
    public void testHashCode() {
        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("Flour", 500, "g"));
        Recipe recipe1 = new Recipe(1, "Cake", ingredients1, "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("Flour", 500, "g"));
        Recipe recipe2 = new Recipe(1, "Cake", ingredients2, "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        assertEquals(recipe1.hashCode(), recipe2.hashCode());
    }

    @Test
    public void testToString() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Flour", 500, "g"));
        ingredients.add(new Ingredient("Sugar", 200, "g"));
        Recipe recipe = new Recipe(1, "Cake", ingredients, "Mix flour and sugar. Bake in oven.", 60, 8, "Best served with whipped cream.");

        String result = recipe.toString();

        String expected = "Cake\n" +
                "Ingredients:\n" +
                "- Flour: 500 g\n" +
                "- Sugar: 200 g\n" +
                "Preparation:\n" +
                "Mix flour and sugar. Bake in oven.\n" +
                "Preparation time: 60 minutes\n" +
                "Servings: 8\n" +
                "Notes:\n" +
                "Best served with whipped cream.\n";
        assertEquals(expected, result);
    }
}