package usecase;

import domain.Recipe;
import domain.RecipeRepository;

import java.util.List;

public class SearchRecipeUseCase {
    private RecipeRepository recipeRepository;

    public SearchRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> execute(String query) {
        return recipeRepository.searchRecipes(query);
    }
}