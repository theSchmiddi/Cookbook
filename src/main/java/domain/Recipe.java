package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private String preparation;
    private int preparationTime;
    private int servings;
    private String notes;

    public Recipe(int id, String name, List<Ingredient> ingredients, String preparation, int preparationTime, int servings, String notes) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        this.preparation = preparation;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.notes = notes;
    }

    public Recipe(String name, List<Ingredient> ingredients, String preparation, int preparationTime, int servings, String notes) {
        this.id = generateId();
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.notes = notes;
    }

    private static int nextId = 1;

    private static int generateId() {
        return nextId++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append("- ").append(ingredient.getName()).append(": ")
                    .append(ingredient.getAmount()).append(" ")
                    .append(ingredient.getUnit()).append("\n");
        }
        sb.append("Preparation:\n").append(preparation).append("\n");
        sb.append("Preparation time: ").append(preparationTime).append(" minutes\n");
        sb.append("Servings: ").append(servings).append("\n");
        sb.append("Notes:\n").append(notes).append("\n");
        return sb.toString();
    }
}