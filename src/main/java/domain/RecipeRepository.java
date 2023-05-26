package domain;

import java.util.List;

public interface RecipeRepository {
    void addRecipe(Recipe recipe);
    List<Recipe> searchRecipes(String query);
    void updateRecipe(int id, Recipe newRecipe);
    void deleteRecipe(int id);
    Recipe randomRecipe();
    Recipe searchRecipesById(int id);
}