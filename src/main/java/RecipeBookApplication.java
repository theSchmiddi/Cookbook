import domain.Recipe;
import framework.RecipeFileRepository;
import usecase.AddRecipeUseCase;
import usecase.DeleteRecipeUseCase;
import usecase.SearchRecipeUseCase;
import usecase.UpdateRecipeUseCase;

import java.util.List;
import java.util.Scanner;

public class RecipeBookApplication {
    private AddRecipeUseCase addRecipeUseCase;
    private SearchRecipeUseCase searchRecipeUseCase;
    private UpdateRecipeUseCase updateRecipeUseCase;
    private DeleteRecipeUseCase deleteRecipeUseCase;

    public RecipeBookApplication() {
        RecipeFileRepository recipeRepository = new RecipeFileRepository();
        addRecipeUseCase = new AddRecipeUseCase(recipeRepository);
        searchRecipeUseCase = new SearchRecipeUseCase(recipeRepository);
        updateRecipeUseCase = new UpdateRecipeUseCase(recipeRepository);
        deleteRecipeUseCase = new DeleteRecipeUseCase(recipeRepository);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Recipe Book");
            System.out.println("1. Add recipe");
            System.out.println("2. Search recipes");
            System.out.println("3. Update recipe");
            System.out.println("4. Delete recipe");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addRecipe();
                    break;
                case 2:
                    searchRecipes();
                    break;
                case 3:
                    updateRecipe();
                    break;
                case 4:
                    deleteRecipe();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ingredients (comma-separated): ");
        List<String> ingredients = List.of(scanner.nextLine().split(","));
        System.out.print("Enter preparation: ");
        String preparation = scanner.nextLine();
        System.out.print("Enter preparation time (minutes): ");
        int preparationTime = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter servings: ");
        int servings = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();
        Recipe recipe = new Recipe(name, ingredients, preparation, preparationTime, servings, notes);
        addRecipeUseCase.execute(recipe);
        System.out.println("Recipe added successfully");
    }

    private void searchRecipes() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        List<Recipe> recipes = searchRecipeUseCase.execute(query);
        if (recipes.isEmpty()) {
            System.out.println("No recipes found");
        } else {
            for (Recipe recipe : recipes) {
                System.out.println(recipe);
            }
        }
    }

    private void updateRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        List<Recipe> recipes = searchRecipeUseCase.execute(name);
        if (recipes.isEmpty()) {
            System.out.println("Recipe not found");
        } else {
            Recipe recipe = recipes.get(0);
            System.out.print("Enter new recipe name (leave blank to keep current name): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                recipe.setName(newName);
            }
            System.out.print("Enter new ingredients (comma-separated, leave blank to keep current ingredients): ");
            String newIngredientsString = scanner.nextLine();
            if (!newIngredientsString.isEmpty()) {
                List<String> newIngredients = List.of(newIngredientsString.split(","));
                recipe.setIngredients(newIngredients);
            }
            System.out.print("Enter new preparation (leave blank to keep current preparation): ");
            String newPreparation = scanner.nextLine();
            if (!newPreparation.isEmpty()) {
                recipe.setPreparation(newPreparation);
            }
            System.out.print("Enter new preparation time (minutes, leave blank to keep current preparation time): ");
            String newPreparationTimeString = scanner.nextLine();
            if (!newPreparationTimeString.isEmpty()) {
                int newPreparationTime = Integer.parseInt(newPreparationTimeString);
                recipe.setPreparationTime(newPreparationTime);
            }
            System.out.print("Enter new servings (leave blank to keep current servings): ");
            String newServingsString = scanner.nextLine();
            if (!newServingsString.isEmpty()) {
                int newServings = Integer.parseInt(newServingsString);
                recipe.setServings(newServings);
            }
            System.out.print("Enter new notes (leave blank to keep current notes): ");
            String newNotes = scanner.nextLine();
            if (!newNotes.isEmpty()) {
                recipe.setNotes(newNotes);
            }
            updateRecipeUseCase.execute(recipe);
            System.out.println("Recipe updated successfully");
        }
    }

    private void deleteRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        List<Recipe> recipes = searchRecipeUseCase.execute(name);
        if (recipes.isEmpty()) {
            System.out.println("Recipe not found");
        } else {
            Recipe recipe = recipes.get(0);
            deleteRecipeUseCase.execute(recipe);
            System.out.println("Recipe deleted successfully");
        }
    }

    public static void main(String[] args) {
        RecipeBookApplication application = new RecipeBookApplication();
        application.run();
    }
}