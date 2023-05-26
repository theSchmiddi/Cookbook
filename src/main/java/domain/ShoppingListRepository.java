package domain;

public interface ShoppingListRepository {
    void saveShoppingList(ShoppingList shoppingList);
    ShoppingList loadShoppingList();
}