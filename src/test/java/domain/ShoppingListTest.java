package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListTest {

    @Test
    public void testAddItem() {
        Ingredient ingredient1 = new Ingredient("Flour", 500, "g");
        Ingredient ingredient2 = new Ingredient("Sugar", 200, "g");
        ShoppingListItem item1 = new ShoppingListItem(ingredient1.getName(), 1, ingredient1.getUnit());
        ShoppingListItem item2 = new ShoppingListItem(ingredient2.getName(), 2, ingredient2.getUnit());
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(item1);
        ShoppingList shoppingList = new ShoppingList(items);

        shoppingList.addItem(item2);

        assertEquals(2, shoppingList.getItems().size());
        assertEquals(1, shoppingList.getItems().get(0).getQuantity());
        assertEquals(2, shoppingList.getItems().get(1).getQuantity());
    }

    @Test
    public void testRemoveItem() {
        Ingredient ingredient1 = new Ingredient("Flour", 500, "g");
        Ingredient ingredient2 = new Ingredient("Sugar", 200, "g");
        ShoppingListItem item1 = new ShoppingListItem(ingredient1.getName(), 1, ingredient1.getUnit());
        ShoppingListItem item2 = new ShoppingListItem(ingredient2.getName(), 2, ingredient2.getUnit());
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        ShoppingList shoppingList = new ShoppingList(items);

        shoppingList.removeItem(item1);

        assertEquals(1, shoppingList.getItems().size());
        assertEquals(item2, shoppingList.getItems().get(0));
    }
}
