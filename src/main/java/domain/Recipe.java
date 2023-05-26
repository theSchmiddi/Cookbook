package domain;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private List<Ingredient> ingredients;
    private String preparation;
    private int preparationTime;
    private int servings;
    private String notes;

    public Recipe(String name, List<Ingredient> ingredients, String preparation, int preparationTime, int servings, String notes) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.notes = notes;
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