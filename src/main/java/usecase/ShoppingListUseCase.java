package usecase;

import domain.*;

import java.util.List;

public class ShoppingListUseCase {
    private final ShoppingListRepository shoppingListRepository;
    private ShoppingList shoppingList;

    public ShoppingListUseCase(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingList = shoppingListRepository.loadShoppingList();
    }

    public void addItemToShoppingList(Ingredient ingredient, int quantity, String unit) {
        shoppingList.addItem(new ShoppingListItem(ingredient.getName(), quantity, unit));
        shoppingListRepository.saveShoppingList(shoppingList);
    }

    public void removeItemFromShoppingList(ShoppingListItem item) {
        shoppingList.removeItem(item);
        shoppingListRepository.saveShoppingList(shoppingList);
    }

    public List<ShoppingListItem> getShoppingList() {
        return shoppingList.getItems();
    }
}