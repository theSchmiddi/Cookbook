package usecase;

import domain.Ingredient;
import domain.ShoppingList;
import domain.ShoppingListItem;
import domain.ShoppingListRepository;

import java.io.IOException;
import java.util.ArrayList;
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

    public void deleteShoppingList() {
        ShoppingList emptyList = new ShoppingList(new ArrayList<>());
        shoppingListRepository.saveShoppingList(emptyList);
        shoppingList = emptyList;
    }

    public List<ShoppingListItem> getShoppingList() {
        return shoppingList.getItems();
    }
}