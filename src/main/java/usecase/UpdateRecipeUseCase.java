package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class UpdateRecipeUseCase {
    private RecipeRepository recipeRepository;

    public UpdateRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void execute(int id, Recipe newRecipe) {
        Recipe oldRecipe = recipeRepository.searchRecipesById(id);
        if (oldRecipe == null) {
            System.out.println("Recipe not found");
            return;
        }
        recipeRepository.updateRecipe(id, newRecipe);
    }
}