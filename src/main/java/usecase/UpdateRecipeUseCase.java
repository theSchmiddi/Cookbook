package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class UpdateRecipeUseCase {
    private RecipeRepository recipeRepository;

    public UpdateRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void execute(Recipe recipe) {
        recipeRepository.updateRecipe(recipe);
    }
}