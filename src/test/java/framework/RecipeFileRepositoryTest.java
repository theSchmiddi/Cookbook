package framework;

import domain.Ingredient;
import domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeFileRepositoryTest {
    private static final String FILENAME = "src/test/resources/test-recipes.txt";
    private RecipeFileRepository repository;

    @BeforeEach
    public void setUp() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME))) {
            pw.println("1|Spaghetti Bolognese|Ground beef:500:g,Tomato sauce:400:g,Spaghetti:400:g|Cook spaghetti. Brown ground beef in a pan. Add tomato sauce and simmer for 10 minutes. Serve over spaghetti.|30|4|");
            pw.println("2|Caesar Salad|Lettuce:1:,Croutons:100:g,Grated Parmesan cheese:50:g,Chicken breast:2:,Caesar dressing:100:ml|Cook chicken breasts and cut into strips. Mix lettuce, croutons, Parmesan cheese, and chicken. Add Caesar dressing and toss.|20|2|");
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository = new RecipeFileRepository(FILENAME);
    }

    @Test
    public void testAddRecipe() {
        Ingredient ingredient1 = new Ingredient("Ground beef", 500, "g");
        Ingredient ingredient2 = new Ingredient("Tomato sauce", 400, "g");
        Ingredient ingredient3 = new Ingredient("Spaghetti", 400, "g");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        Recipe recipe = new Recipe(0,"Spaghetti Carbonara", ingredients, "Cook spaghetti. Brown ground beef in a pan. Add tomato sauce and simmer for 10 minutes. Serve over spaghetti.", 30, 4, "");
        repository.addRecipe(recipe);

        Recipe addedRecipe = repository.searchRecipesById(3);

        assertEquals("Spaghetti Carbonara", addedRecipe.getName());
        assertEquals(3, addedRecipe.getId());

        Ingredient expectedIngredient = new Ingredient("Ground beef", 500, "g");
        Ingredient actualIngredient = addedRecipe.getIngredients().get(0);
        assertEquals(expectedIngredient.getName(), actualIngredient.getName());
        assertEquals(expectedIngredient.getAmount(), actualIngredient.getAmount());
        assertEquals(expectedIngredient.getUnit(), actualIngredient.getUnit());

        assertEquals("Cook spaghetti. Brown ground beef in a pan. Add tomato sauce and simmer for 10 minutes. Serve over spaghetti.", addedRecipe.getPreparation());
        assertEquals(30, addedRecipe.getPreparationTime());
        assertEquals(4, addedRecipe.getServings());
        assertEquals("", addedRecipe.getNotes());
    }

    @Test
    public void testSearchRecipes() {
        List<Recipe> recipes = repository.searchRecipes("salad");

        assertEquals(1, recipes.size());
        assertEquals("Caesar Salad", recipes.get(0).getName());
    }

    @Test
    public void testDeleteRecipe() {
        repository.deleteRecipe(1);

        Recipe deletedRecipe = repository.searchRecipesById(1);

        assertNull(deletedRecipe);
    }

    @Test
    public void testRandomRecipe() {
        Recipe randomRecipe = repository.randomRecipe();

        assertNotNull(randomRecipe);
    }
}
