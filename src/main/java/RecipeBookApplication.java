import domain.*;
import framework.RecipeFileRepository;
import framework.ShoppingListFileRepository;
import usecase.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeBookApplication {
    private final AddRecipeUseCase addRecipeUseCase;
    private final SearchRecipeUseCase searchRecipeUseCase;
    private final UpdateRecipeUseCase updateRecipeUseCase;
    private final DeleteRecipeUseCase deleteRecipeUseCase;
    private final RandomRecipeUseCase randomRecipeUseCase;
    private final ShoppingListUseCase shoppingListUseCase;
    private final ShoppingListRepository shoppingListRepository;

    public RecipeBookApplication() {
        RecipeRepository recipeRepository = new RecipeFileRepository();
        addRecipeUseCase = new AddRecipeUseCase(recipeRepository);
        searchRecipeUseCase = new SearchRecipeUseCase(recipeRepository);
        updateRecipeUseCase = new UpdateRecipeUseCase(recipeRepository);
        deleteRecipeUseCase = new DeleteRecipeUseCase(recipeRepository);
        randomRecipeUseCase = new RandomRecipeUseCase(recipeRepository);

        shoppingListRepository = new ShoppingListFileRepository();
        shoppingListUseCase = new ShoppingListUseCase(shoppingListRepository);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Recipe Book");
            System.out.println("1. Add recipe");
            System.out.println("2. Search recipes");
            System.out.println("3. Update recipe");
            System.out.println("4. Random recipe");
            System.out.println("5. Delete recipe");
            System.out.println("6. Add recipe to shopping list");
            System.out.println("7. Remove item from shopping list");
            System.out.println("8. View shopping list");
            System.out.println("9. Delete shopping list");
            System.out.println("10. Exit");
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
                    randomRecipe();
                    break;
                case 5:
                    deleteRecipe();
                    break;
                case 6:
                    addRecipeToShoppingList();
                    break;
                case 7:
                    removeItemFromShoppingList();
                    break;
                case 8:
                    viewShoppingList();
                    break;
                case 9:
                    deleteShoppingList();
                    break;
                case 10:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void randomRecipe() {
        Recipe randomRecipe = randomRecipeUseCase.execute();
        if (randomRecipe == null) {
            System.out.println("No recipes found");
        } else {
            System.out.println("Random recipe:");
            System.out.println(randomRecipe);
        }
    }

    private void addRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ingredients (comma-separated): ");
        List<String> ingredientStrings = List.of(scanner.nextLine().split(","));
        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredientString : ingredientStrings) {
            int amount = 0;
            while (true) {
                System.out.print("Enter amount for " + ingredientString.trim() + ": ");
                String amountString = scanner.nextLine();
                try {
                    amount = Integer.parseInt(amountString.trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, please enter a number");
                }
            }
            String unit = "";
            while (true) {
                System.out.print("Enter unit for " + ingredientString.trim() + ": ");
                unit = scanner.nextLine().trim();
                if (!unit.isEmpty()) {
                    break;
                } else {
                    System.out.println("Invalid input, please enter a non-empty unit");
                }
            }
            Ingredient ingredient = new Ingredient(ingredientString.trim(), amount, unit);
            ingredients.add(ingredient);
        }
        System.out.print("Enter preparation: ");
        String preparation = scanner.nextLine();
        int preparationTime = 0;
        while (true) {
            System.out.print("Enter preparation time (minutes): ");
            try {
                preparationTime = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
        int servings = 0;
        while (true) {
            System.out.print("Enter servings: ");
            try {
                servings = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
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
                System.out.println("Found " + recipes.size() + " recipes:");
                for (int i = 0; i < recipes.size(); i++) {
                    System.out.println((i + 1) + ". " + recipes.get(i).getName());
                }
                int choice = 0;
                while (true) {
                    System.out.print("Select a recipe (or enter 0 to cancel): ");
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                        if (choice == 0) {
                            return;
                        }
                        if (choice < 1 || choice > recipes.size()) {
                            throw new NumberFormatException();
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, please enter a number between 1 and " + recipes.size() + " (or enter 0 to cancel)");
                    }
                }
                Recipe selectedRecipe = recipes.get(choice - 1);
                System.out.println(selectedRecipe);
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
                System.out.println("Current recipe:");
                System.out.println(recipe);
                System.out.println("Enter new recipe information (leave blank to keep current information):");
                System.out.print("Name: ");
                String newName = scanner.nextLine();
                if (!newName.isBlank()) {
                    recipe.setName(newName);
                }
                System.out.print("Ingredients (comma-separated): ");
                String newIngredientsString = scanner.nextLine();
                if (!newIngredientsString.isBlank()) {
                    List<String> newIngredientsStringList = List.of(newIngredientsString.split(","));
                    List<Ingredient> newIngredients = new ArrayList<>();
                    for (String ingredientString : newIngredientsStringList) {
                        Ingredient ingredient = new Ingredient(ingredientString.trim(), 0, "");
                        newIngredients.add(ingredient);
                    }
                    recipe.setIngredients(newIngredients);
                }
                System.out.print("Preparation: ");
                String newPreparation = scanner.nextLine();
                if (!newPreparation.isBlank()) {
                    recipe.setPreparation(newPreparation);
                }
                System.out.print("Preparation time (minutes): ");
                String newPreparationTimeString = scanner.nextLine();
                if (!newPreparationTimeString.isBlank()) {
                    try {
                        int newPreparationTime = Integer.parseInt(newPreparationTimeString);
                        recipe.setPreparationTime(newPreparationTime);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for preparation time, keeping current value");
                    }
                }
                System.out.print("Servings: ");
                String newServingsString = scanner.nextLine();
                if (!newServingsString.isBlank()) {
                    try {
                        int newServings = Integer.parseInt(newServingsString);
                        recipe.setServings(newServings);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for servings, keeping current value");
                    }
                }
                System.out.print("Notes: ");
                String newNotes = scanner.nextLine();
                if (!newNotes.isBlank()) {
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
                System.out.println("Found " + recipes.size() + " recipes:");
                for (int i = 0; i < recipes.size(); i++) {
                    System.out.println((i + 1) + ". " + recipes.get(i).getName());
                }
                System.out.print("Select a recipe to delete: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                Recipe selectedRecipe = recipes.get(choice - 1);
                deleteRecipeUseCase.execute(selectedRecipe);
                System.out.println("Recipe deleted successfully");
            }
        }

    private void addRecipeToShoppingList() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        List<Recipe> recipes = searchRecipeUseCase.execute(name);
        if (recipes.isEmpty()) {
            System.out.println("Recipe not found");
        } else {
            System.out.println("Select a recipe to add to shopping list:");
            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                System.out.println((i + 1) + ". " + recipe.getName());
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 1 || choice > recipes.size()) {
                System.out.println("Invalid choice");
            } else {
                Recipe recipe = recipes.get(choice - 1);
                System.out.print("Enter number of servings: ");
                int servings = scanner.nextInt();
                scanner.nextLine();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    int quantity = (int) Math.ceil(servings * (ingredient.getAmount() / (double) recipe.getServings()));
                    shoppingListUseCase.addItemToShoppingList(ingredient, quantity, ingredient.getUnit());
                }
                System.out.println("Ingredients added to shopping list");
                System.out.println("Your current shopping list:");
                viewShoppingList();
                System.out.println();
            }
        }
    }

    private void removeItemFromShoppingList() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        ShoppingListItem itemToRemove = null;
        for (ShoppingListItem item : shoppingListUseCase.getShoppingList()) {
            if (item.getIngredient().equalsIgnoreCase(name)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            shoppingListUseCase.removeItemFromShoppingList(itemToRemove);
            System.out.println("Item removed from shopping list");
        } else {
            System.out.println("Item not found in shopping list");
        }
        System.out.println("Your current shopping list:");
        viewShoppingList();
        System.out.println();
    }

    private void viewShoppingList() {
        List<ShoppingListItem> shoppingList = shoppingListUseCase.getShoppingList();
        if (shoppingList.isEmpty()) {
            System.out.println("Shopping list is empty");
        } else {
            System.out.println("Shopping list:");
            for (int i = 0; i < shoppingList.size(); i++) {
                ShoppingListItem item = shoppingList.get(i);
                System.out.println((i + 1) + ". " + item.getQuantity() + " " + item.getUnit() + " of " + item.getIngredient());
            }
        }
        System.out.println();
    }
    private void deleteShoppingList() {
        shoppingListUseCase.deleteShoppingList();
        System.out.println("Shopping list deleted");
    }

        public static void main(String[] args) {
            RecipeBookApplication application = new RecipeBookApplication();
            application.run();
        }
    }