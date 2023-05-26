package usecase;

import domain.Recipe;
import domain.RecipeRepository;

public class RandomRecipeUseCase {

    private RecipeRepository recipeRepository;

    public RandomRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe execute() {
        return recipeRepository.randomRecipe();
    }
}
