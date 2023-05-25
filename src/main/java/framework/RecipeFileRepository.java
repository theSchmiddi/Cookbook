package framework;

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
                    Recipe r = new Recipe(recipe.getName(), null, null, 0, 0, null);
                    recipes.add(r);
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
        sb.append(String.join(",", recipe.getIngredients())).append("|");
        sb.append(recipe.getPreparation()).append("|");
        sb.append(recipe.getPreparationTime()).append("|");
        sb.append(recipe.getServings()).append("|");
        sb.append(recipe.getNotes());
        return sb.toString();
    }

    private Recipe stringToRecipe(String line) {
        String[] parts = line.split("\\|");
        String name = parts[0];
        List<String> ingredients = List.of(parts[1].split(","));
        String preparation = parts[2];
        int preparationTime = Integer.parseInt(parts[3]);
        int servings = Integer.parseInt(parts[4]);
        String notes = parts[5];
        return new Recipe(name, ingredients, preparation, preparationTime,servings ,notes);
    }
}