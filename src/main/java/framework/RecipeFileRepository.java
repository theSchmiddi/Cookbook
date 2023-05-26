package framework;

import domain.Ingredient;
import domain.Recipe;
import domain.RecipeRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecipeFileRepository implements RecipeRepository {
    private static final String FILENAME = "src/main/resources/recipes.txt";

    @Override
    public void addRecipe(Recipe recipe) {
        int id = getNextId();
        recipe = new Recipe(id, recipe.getName(), recipe.getIngredients(), recipe.getPreparation(), recipe.getPreparationTime(), recipe.getServings(), recipe.getNotes());
        try (FileWriter fw = new FileWriter(FILENAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(recipeToString(recipe));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Recipe> searchRecipes(String query) {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe recipe = stringToRecipe(line);
                if (recipe.getName().toLowerCase().contains(query.toLowerCase())) {
                    recipes.add(recipe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public void updateRecipe(int id, Recipe newRecipe) {
        List<Recipe> recipes = readRecipesFromFile();
        boolean recipeFound = false;
        for (int i = 0; i < recipes.size(); i++) {
            Recipe r = recipes.get(i);
            if (r.getId() == id) {
                recipes.set(i, newRecipe);
                recipeFound = true;
                break;
            }
        }
        if (!recipeFound) {
            System.out.println("Recipe not found");
            return;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME))) {
            for (Recipe r : recipes) {
                pw.println(recipeToString(r));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecipe(int id) {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe r = stringToRecipe(line);
                if (r.getId() != id) {
                    recipes.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME))) {
            for (Recipe r : recipes) {
                pw.println(recipeToString(r));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Recipe randomRecipe() {
        List<Recipe> recipes = readRecipesFromFile();
        if (recipes.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            return recipes.get(random.nextInt(recipes.size()));
        }
    }

    private int getNextId() {
        int nextId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    @Override
    public Recipe searchRecipesById(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe recipe = stringToRecipe(line);
                if (recipe.getId() == id) {
                    return recipe;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Recipe> readRecipesFromFile() {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Recipe recipe = stringToRecipe(line);
                recipes.add(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private String recipeToString(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(recipe.getId()).append("|");
        sb.append(recipe.getName()).append("|");
        for (Ingredient ingredient : recipe.getIngredients()) {
            sb.append(ingredient.getName());
            if (ingredient.getAmount() != 0) {
                sb.append(":").append(ingredient.getAmount());
            }
            sb.append(":").append(ingredient.getUnit());
            sb.append(",");
        }
        sb.append("|");
        sb.append(recipe.getPreparation()).append("|");
        sb.append(recipe.getPreparationTime()).append("|");
        sb.append(recipe.getServings()).append("|");
        sb.append(recipe.getNotes());
        return sb.toString();
    }

    private Recipe stringToRecipe(String line) {
        String[] parts = line.split("\\|");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        List<Ingredient> ingredients = new ArrayList<>();
        String[] ingredientParts = parts[2].split(",");
        for (String ingredientPart : ingredientParts) {
            String[] ingredient = ingredientPart.split(":");
            String ingredientName = ingredient[0];
            int ingredientAmount = 0;
            String ingredientUnit = "";
            if (ingredient.length > 1) {
                ingredientAmount = Integer.parseInt(ingredient[1]);
            }
            if (ingredient.length > 2) {
                ingredientUnit = ingredient[2];
            }
            ingredients.add(new Ingredient(ingredientName, ingredientAmount, ingredientUnit));
        }
        String preparation = parts[3];
        int preparationTime = Integer.parseInt(parts[4]);
        int servings = Integer.parseInt(parts[5]);
        String notes = "";
        if (parts.length > 6) {
            notes = parts[6];
        }
        return new Recipe(id, name, ingredients, preparation, preparationTime, servings, notes);
    }
}