package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class DeleteRecipeUseCase {
    private RecipeRepository recipeRepository;

    public DeleteRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void execute(Recipe recipe) {
        recipeRepository.deleteRecipe(recipe);
    }
}