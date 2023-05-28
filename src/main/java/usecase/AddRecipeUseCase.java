package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class AddRecipeUseCase {
    private RecipeRepository recipeRepository;

    public AddRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void execute(Recipe recipe) {
        recipeRepository.addRecipe(recipe);
    }
}