package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class DeleteRecipeUseCase {
    private RecipeRepository recipeRepository;

    public DeleteRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void execute(int id) {
        Recipe recipe = recipeRepository.searchRecipesById(id);
        if (recipe == null) {
            System.out.println("Recipe not found");
            return;
        }
        recipeRepository.deleteRecipe(id);
    }
}