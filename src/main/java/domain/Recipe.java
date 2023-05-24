package domain;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private List<String> ingredients;
    private String preparation;
    private int preparationTime;
    private int servings;
    private String notes;

    public Recipe(String name, List<String> ingredients, String preparation, int preparationTime,int servings, String notes) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.notes = notes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Ingredients: ").append("\n");
        for (String ingredient : ingredients) {
            sb.append("- ").append(ingredient).append("\n");
        }
        sb.append("Preparation: ").append(preparation).append("\n");
        sb.append("Preparation time: ").append(preparationTime).append(" minutes").append("\n");
        sb.append("Servings: ").append(servings).append("\n");
        sb.append("Notes: ").append(notes).append("\n");
        return sb.toString();
    }
}