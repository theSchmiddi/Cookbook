package ui;

import domain.Ingredient;
import domain.Recipe;
import domain.ShoppingListItem;
import usecase.DeleteRecipeUseCase;
import usecase.SearchRecipeUseCase;
import usecase.ShoppingListUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final SearchRecipeUseCase searchRecipeUseCase;
    private final DeleteRecipeUseCase deleteRecipeUseCase;
    private final ShoppingListUseCase shoppingListUseCase;


    public ConsoleUI(SearchRecipeUseCase searchRecipeUseCase, DeleteRecipeUseCase deleteRecipeUseCase, ShoppingListUseCase shoppingListUseCase) {
        scanner = new Scanner(System.in);
        this.searchRecipeUseCase = searchRecipeUseCase;
        this.deleteRecipeUseCase = deleteRecipeUseCase;
        this.shoppingListUseCase = shoppingListUseCase;
    }

    public void displayMenu() {
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
    }

    public String readOption() {
        System.out.print("Enter option: ");
        return scanner.nextLine();
    }

    public Recipe chooseRecipe(List<Recipe> recipes) {
        System.out.println("Found " + recipes.size() + " recipes:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }

        int choice;
        while (true) {
            System.out.print("Select a recipe (or enter 0 to cancel): ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice == 0) {
                    return null;
                }
                if (choice < 1 || choice > recipes.size()) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number between 1 and " + recipes.size() + " (or enter 0 to cancel)");
            }
        }

        return recipes.get(choice - 1);
    }

    public void displayRandomRecipe(Recipe randomRecipe) {
        if (randomRecipe == null) {
            System.out.println("No recipes found");
        } else {
            System.out.println("Random recipe:");
            System.out.println(randomRecipe);
        }
    }

    public Recipe createRecipe() {
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();

        System.out.print("Enter ingredients (comma-separated): ");
        List<String> ingredientStrings = List.of(scanner.nextLine().split(","));
        List<Ingredient> ingredients = new ArrayList<>();

        for (String ingredientString : ingredientStrings) {
            int amount;
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

            String unit;
            do {
                System.out.print("Enter unit for " + ingredientString.trim() + ": ");
                unit = scanner.nextLine().trim();
            } while (unit.isEmpty());

            Ingredient ingredient = new Ingredient(ingredientString.trim(), amount, unit);
            ingredients.add(ingredient);
        }

        System.out.print("Enter preparation: ");
        String preparation = scanner.nextLine();

        int preparationTime;
        while (true) {
            System.out.print("Enter preparation time (minutes): ");
            try {
                preparationTime = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }

        int servings;
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

        return new Recipe(0, name, ingredients, preparation, preparationTime, servings, notes);
    }

    public void displayRecipeAdded() {
        System.out.println("Recipe added successfully");
    }

    public String getSearchQuery() {
        System.out.print("Enter search query: ");
        return scanner.nextLine();
    }

    public void displayNoRecipesFound() {
        System.out.println("No recipes found");
    }

    public void displayRecipes(List<Recipe> recipes) {
        Recipe selectedRecipe = chooseRecipe(recipes);
        if (selectedRecipe != null) {
            System.out.println(selectedRecipe);
        }
    }

    public Recipe updateRecipe(Recipe recipe) {
        System.out.println("Current recipe:");
        System.out.println(recipe);
        System.out.println("Enter new recipe information (leave blank to keep current information):");

        System.out.print("Name: ");
        String newName = scanner.nextLine();
        if (newName.isBlank()) {
            newName = recipe.getName();
        }

        System.out.print("Ingredients (comma-separated): ");
        String newIngredientsString = scanner.nextLine();
        List<Ingredient> newIngredients = null;
        if (!newIngredientsString.isBlank()) {
            List<String> newIngredientsStringList = List.of(newIngredientsString.split(","));
            newIngredients = new ArrayList<>();
            for (String ingredientString : newIngredientsStringList) {
                Ingredient currentIngredient = recipe.getIngredientByName(ingredientString.trim());
                if (currentIngredient == null) {
                    System.out.println("Invalid ingredient name: " + ingredientString.trim());
                    continue;
                }
                int newAmount = currentIngredient.getAmount();
                while (true) {
                    System.out.print("Enter amount for " + ingredientString.trim() + " (or leave blank to keep current amount): ");
                    String newAmountString = scanner.nextLine();
                    if (newAmountString.isBlank()) {
                        break;
                    }
                    try {
                        newAmount = Integer.parseInt(newAmountString.trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, please enter a number");
                    }
                }
                String newUnit = currentIngredient.getUnit();
                while (true) {
                    System.out.print("Enter unit for " + ingredientString.trim() + " (or leave blank to keep current unit): ");
                    String newUnitString = scanner.nextLine().trim();
                    if (newUnitString.isBlank()) {
                        break;
                    }
                    if (newUnitString.isEmpty()) {
                        System.out.println("Invalid input, please enter a non-empty unit");
                    } else {
                        newUnit = newUnitString;
                        break;
                    }
                }
                Ingredient newIngredient = new Ingredient(ingredientString.trim(), newAmount, newUnit);
                newIngredients.add(newIngredient);
            }
        }

        System.out.print("Preparation: ");
        String newPreparation = scanner.nextLine();
        if (newPreparation.isBlank()) {
            newPreparation = recipe.getPreparation();
        }

        int newPreparationTime = recipe.getPreparationTime();
        while (true) {
            System.out.print("Preparation time (minutes): ");
            String newPreparationTimeString = scanner.nextLine();
            if (newPreparationTimeString.isBlank()) {
                break;
            }
            try {
                newPreparationTime = Integer.parseInt(newPreparationTimeString);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }

        int newServings = recipe.getServings();
        while (true) {
            System.out.print("Servings: ");
            String newServingsString = scanner.nextLine();
            if (newServingsString.isBlank()) {
                break;
            }
            try {
                newServings = Integer.parseInt(newServingsString);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for servings, please enter a number");
            }
        }

        System.out.print("Notes: ");
        String newNotes = scanner.nextLine();
        if (newNotes.isBlank()) {
            newNotes = recipe.getNotes();
        }

        return new Recipe(recipe.getId(), newName, newIngredients, newPreparation, newPreparationTime, newServings, newNotes);
    }

    public void deleteRecipe() {
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
            deleteRecipeUseCase.execute(selectedRecipe.getId());
            System.out.println("Recipe deleted successfully");
        }
    }

    public void addRecipeToShoppingList() {
        String name = readRecipeName();
        List<Recipe> recipes = searchRecipeUseCase.execute(name);
        if (recipes.isEmpty()) {
            displayMessage("Recipe not found");
        } else {
            displayMessage("Select a recipe to add to shopping list:");
            Recipe selectedRecipe = chooseRecipe(recipes);
            if (selectedRecipe == null) {
                return;
            }
            int servings = readServings();
            for (Ingredient ingredient : selectedRecipe.getIngredients()) {
                int quantity = (int) Math.ceil(servings * (ingredient.getAmount() / (double) selectedRecipe.getServings()));
                shoppingListUseCase.addItemToShoppingList(ingredient, quantity, ingredient.getUnit());
            }
            displayMessage("Ingredients added to shopping list");
            displayMessage("Your current shopping list:");
            viewShoppingList();
        }
    }

    public void viewShoppingList() {
        List<ShoppingListItem> shoppingList = shoppingListUseCase.getShoppingList();
        if (shoppingList.isEmpty()) {
            displayMessage("Shopping list is empty");
        } else {
            displayMessage("Shopping list:");
            for (int i = 0; i < shoppingList.size(); i++) {
                ShoppingListItem item = shoppingList.get(i);
                displayMessage((i + 1) + ". " + item.getQuantity() + " " + item.getUnit() + " of " + item.getIngredient());
            }
        }
    }

    private int readServings() {
        while (true) {
            System.out.print("Enter number of servings: ");
            String input = scanner.nextLine();
            try {
                int servings = Integer.parseInt(input);
                if (servings <= 0) {
                    throw new NumberFormatException();
                }
                return servings;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a positive integer");
            }
        }
    }

    public void removeItemFromShoppingList() {
        String name = readItemName();
        ShoppingListItem itemToRemove = findItemByName(name);
        if (itemToRemove == null) {
            displayMessage("Item not found in shopping list");
        } else {
            shoppingListUseCase.removeItemFromShoppingList(itemToRemove);
            displayMessage("Item removed from shopping list");
            displayMessage("Your current shopping list:");
            viewShoppingList();
        }
    }

    private String readItemName() {
        System.out.print("Enter item name: ");
        return scanner.nextLine();
    }

    private ShoppingListItem findItemByName(String name) {
        for (ShoppingListItem item : shoppingListUseCase.getShoppingList()) {
            if (item.getIngredient().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public String readRecipeName() {
        System.out.print("Enter recipe name: ");
        scanner.nextLine();
        return scanner.nextLine();
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }
}