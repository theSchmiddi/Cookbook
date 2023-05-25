package framework;

import domain.Ingredient;
import domain.Recipe;
import domain.RecipeRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeFileRepository implements RecipeRepository {
    private static final String FILENAME = "src/main/resources/recipes.txt";

    @Override
    public void addRecipe(Recipe recipe) {
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
    public void updateRecipe(Recipe recipe) {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe r = stringToRecipe(line);
                if (r.getName().equals(recipe.getName())) {
                    recipes.add(recipe);
                } else {
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
    public void deleteRecipe(Recipe recipe) {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe r = stringToRecipe(line);
                if (!r.getName().equals(recipe.getName())) {
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

    public Recipe selectRecipe(String recipeName) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Recipe recipe = stringToRecipe(line);
                if (recipe.getName().equals(recipeName)) {
                    return recipe;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String recipeToString(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(recipe.getName()).append("|");
        for (Ingredient ingredient : recipe.getIngredients()) {
            sb.append(ingredient.getName());
            if (!ingredient.getAmount().isEmpty()) {
                sb.append(":").append(ingredient.getAmount());
            }
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
        String name = parts[0];
        List<Ingredient> ingredients = new ArrayList<>();
        String[] ingredientParts = parts[1].split(",");
        for (String ingredientPart : ingredientParts) {
            String[] ingredient = ingredientPart.split(":");
            String ingredientName = ingredient[0];
            String ingredientAmount = "";
            if (ingredient.length > 1) {
                ingredientAmount = ingredient[1];
            }
            ingredients.add(new Ingredient(ingredientName, ingredientAmount));
        }
        String preparation = parts[2];
        int preparationTime = Integer.parseInt(parts[3]);
        int servings = Integer.parseInt(parts[4]);
        String notes = "";
        if (parts.length > 5) {
            notes = parts[5];
        }
        return new Recipe(name, ingredients, preparation, preparationTime, servings, notes);
    }
}