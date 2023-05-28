import domain.*;
import framework.RecipeFileRepository;
import framework.ShoppingListFileRepository;
import usecase.*;
import domain.Recipe;
import ui.ConsoleUI;

import java.util.List;

public class RecipeBookApplication {
    private final AddRecipeUseCase addRecipeUseCase;
    private final SearchRecipeUseCase searchRecipeUseCase;
    private final UpdateRecipeUseCase updateRecipeUseCase;
    private final RandomRecipeUseCase randomRecipeUseCase;
    private final ShoppingListUseCase shoppingListUseCase;
    private final ConsoleUI consoleUI;


    public RecipeBookApplication() {
        RecipeRepository recipeRepository = new RecipeFileRepository();
        addRecipeUseCase = new AddRecipeUseCase(recipeRepository);
        searchRecipeUseCase = new SearchRecipeUseCase(recipeRepository);
        updateRecipeUseCase = new UpdateRecipeUseCase(recipeRepository);
        DeleteRecipeUseCase deleteRecipeUseCase = new DeleteRecipeUseCase(recipeRepository);
        randomRecipeUseCase = new RandomRecipeUseCase(recipeRepository);

        ShoppingListRepository shoppingListRepository = new ShoppingListFileRepository();
        shoppingListUseCase = new ShoppingListUseCase(shoppingListRepository);

        consoleUI = new ConsoleUI(searchRecipeUseCase, deleteRecipeUseCase, shoppingListUseCase);
    }

    public void run() {
        int option = -1;

        while (option != 10) {
            consoleUI.displayMenu();
            String input = consoleUI.readOption();

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                consoleUI.displayMessage("Invalid option!");
                continue;
            }

            switch (option) {
                case 1 -> addRecipe();
                case 2 -> searchRecipes();
                case 3 -> updateRecipe();
                case 4 -> randomRecipe();
                case 5 -> deleteRecipe();
                case 6 -> addRecipeToShoppingList();
                case 7 -> removeItemFromShoppingList();
                case 8 -> viewShoppingList();
                case 9 -> deleteShoppingList();
                case 10 -> consoleUI.displayMessage("Goodbye!");
                default -> consoleUI.displayMessage("Invalid option!");
            }
        }
    }

    private void randomRecipe() {
        Recipe randomRecipe = randomRecipeUseCase.execute();
        consoleUI.displayRandomRecipe(randomRecipe);
    }

    private void addRecipe() {
        Recipe recipe = consoleUI.createRecipe();
        addRecipeUseCase.execute(recipe);
        consoleUI.displayRecipeAdded();
    }

    private void searchRecipes() {
        String query = consoleUI.getSearchQuery();
        List<Recipe> recipes = searchRecipeUseCase.execute(query);
        if (recipes.isEmpty()) {
            consoleUI.displayNoRecipesFound();
        } else {
            consoleUI.displayRecipes(recipes);
        }
    }

    private void updateRecipe() {
        String query = consoleUI.getSearchQuery();
        List<Recipe> recipes = searchRecipeUseCase.execute(query);
        if (recipes.isEmpty()) {
            consoleUI.displayNoRecipesFound();
        } else {
            Recipe selectedRecipe = consoleUI.chooseRecipe(recipes);
            if (selectedRecipe != null) {
                Recipe updatedRecipe = consoleUI.updateRecipe(selectedRecipe);
                updateRecipeUseCase.execute(selectedRecipe.getId(), updatedRecipe);
                consoleUI.displayMessage("Recipe updated successfully");
            }
        }
    }

    public void deleteRecipe() {
        consoleUI.deleteRecipe();
    }

    public void addRecipeToShoppingList() {
        consoleUI.addRecipeToShoppingList();
    }

    private void removeItemFromShoppingList() {
       consoleUI.removeItemFromShoppingList();
    }

    public void viewShoppingList() {
        consoleUI.viewShoppingList();
    }

    private void deleteShoppingList() {
        shoppingListUseCase.deleteShoppingList();
        consoleUI.displayMessage("Shopping list deleted");
    }

    public static void main(String[] args) {
        RecipeBookApplication application = new RecipeBookApplication();
        application.run();
    }
}