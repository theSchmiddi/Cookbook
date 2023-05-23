package domain;

import java.util.List;

public interface RecipeRepository {
    void addRecipe(Recipe recipe);
    List<Recipe> searchRecipes(String query);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
}