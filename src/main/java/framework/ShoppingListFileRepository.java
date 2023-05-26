package framework;

import domain.ShoppingList;
import domain.ShoppingListItem;
import domain.ShoppingListRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListFileRepository implements ShoppingListRepository {
    private static final String FILENAME = "src/main/resources/shoppingList.txt";

    @Override
    public void saveShoppingList(ShoppingList shoppingList) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME))) {
            for (ShoppingListItem item : shoppingList.getItems()) {
                pw.println(itemToString(item));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingList loadShoppingList() {
        List<ShoppingListItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    ShoppingListItem item = stringToItem(line);
                    if (item != null) {
                        items.add(item);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ShoppingList(items);
    }

    private String itemToString(ShoppingListItem item) {
        return item.getIngredient() + ":" +
                item.getQuantity() + " " +
                item.getUnit();
    }

    private ShoppingListItem stringToItem(String line) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
            String ingredient = parts[0];
            String[] quantityAndUnit = parts[1].split(" ");
            if (quantityAndUnit.length == 2) {
                int quantity = Integer.parseInt(quantityAndUnit[0]);
                String unit = quantityAndUnit[1];
                return new ShoppingListItem(ingredient, quantity, unit);
            }
        }
        return null;
    }
}