package domain;

import java.util.List;

public class ShoppingList {
    private final List<ShoppingListItem> items;

    public ShoppingList(List<ShoppingListItem> items) {
        this.items = items;
    }

    public void addItem(ShoppingListItem item) {
        for (ShoppingListItem listItem : items) {
            if (listItem.getIngredient().equals(item.getIngredient()) && listItem.getUnit().equals(item.getUnit())) {
                listItem.increaseQuantity(item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(ShoppingListItem item) {
        items.remove(item);
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }
}