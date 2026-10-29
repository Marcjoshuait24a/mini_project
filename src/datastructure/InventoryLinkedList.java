package datastructure;

import model.Product;

public class InventoryLinkedList {
    private InventoryNode head;
    private int size;

    // Constructor
    public InventoryLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // 1. ADD / INSERT OPERATION (Appends product to the end of the list)
    public void addProduct(Product product) {
        InventoryNode newNode = new InventoryNode(product);
        if (head == null) {
            head = newNode;
        } else {
            InventoryNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // 2. MANUAL LINEAR SEARCH (Required by IT20 rules, searches by product name)
    public Product searchByName(String targetName) {
        if (targetName == null || head == null) {
            return null;
        }
        
        InventoryNode current = head;
        String cleanTarget = targetName.trim();

        // Traverse the linked list manually node by node
        while (current != null) {
            if (current.product.getName().equalsIgnoreCase(cleanTarget)) {
                return current.product; // Found match
            }
            current = current.next;
        }
        
        return null; // Not found after checking all nodes
    }

    // 3. UPDATE STOCK OPERATION (Finds item manually and modifies stock)
    public boolean updateStockByName(String targetName, int newStockQuantity) {
        Product foundProduct = searchByName(targetName);
        if (foundProduct != null) {
            foundProduct.setStockQuantity(newStockQuantity);
            return true;
        }
        return false; // Product not found, update failed
    }

    // 4. RETRIEVE ALL RECORDS AS A PLAIN ARRAY (For populating the GUI table/display)
    // Uses standard Java arrays instead of java.util.ArrayList to comply with rules.
    public Product[] getAllProductsArray() {
        Product[] productArray = new Product[size];
        InventoryNode current = head;
        int index = 0;
        
        while (current != null) {
            productArray[index++] = current.product;
            current = current.next;
        }
        
        return productArray;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Get current total item count
    public int size() {
        return size;
    }
}