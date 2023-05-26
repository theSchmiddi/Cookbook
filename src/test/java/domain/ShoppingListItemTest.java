package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingListItemTest {

    @Test
    public void testGetIngredient() {
        ShoppingListItem item = new ShoppingListItem("Flour", 500, "g");

        String result = item.getIngredient();

        assertEquals("Flour", result);
    }

    @Test
    public void testGetQuantity() {
        ShoppingListItem item = new ShoppingListItem("Sugar", 200, "g");

        int result = item.getQuantity();

        assertEquals(200, result);
    }

    @Test
    public void testSetQuantity() {
        ShoppingListItem item = new ShoppingListItem("Eggs", 3, "");

        item.setQuantity(6);

        assertEquals(6, item.getQuantity());
    }

    @Test
    public void testIncreaseQuantity() {
        ShoppingListItem item = new ShoppingListItem("Butter", 100, "g");

        item.increaseQuantity(50);

        assertEquals(150, item.getQuantity());
    }

    @Test
    public void testGetUnit() {
        ShoppingListItem item = new ShoppingListItem("Milk", 1, "l");

        String result = item.getUnit();

        assertEquals("l", result);
    }

    @Test
    public void testSetUnit() {
        ShoppingListItem item = new ShoppingListItem("Cheese", 200, "g");

        item.setUnit("kg");

        assertEquals("kg", item.getUnit());
    }
}
