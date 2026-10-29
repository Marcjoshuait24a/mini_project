package gui;

import datastructure.InventoryLinkedList;
import model.EquipmentProduct;
import model.Product;
import model.SeedProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryDashboard extends JFrame {
    private InventoryLinkedList inventoryList;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtPrice, txtStock, txtExtraField1, txtExtraField2, txtSearch;
    private JComboBox<String> cmbProductType;
    private JLabel lblExtra1, lblExtra2;

    public InventoryDashboard() {
        inventoryList = new InventoryLinkedList();
        loadSampleData();

        setTitle("Agricultural Supply Inventory System (IT20 & IT21 Project)");
        setSize(1100, 650); // Wider frame for better side-by-side view
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- TOP: TITLE ---
        JPanel panelTop = new JPanel();
        JLabel lblTitle = new JLabel("Agricultural Supply Inventory Management (Scenario #5)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panelTop.add(lblTitle);
        add(panelTop, BorderLayout.NORTH);

        // --- CENTER: TABLE DISPLAY ---
        String[] columnNames = {"ID", "Name", "Type", "Price (₱)", "Stock", "Category Specific Details"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        refreshTableData();
        
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Inventory Records"));
        add(scrollPane, BorderLayout.CENTER);

        // --- RIGHT: CONTROLS & FORMS (Organized with GridBagLayout / Flow) ---
        JPanel panelControls = new JPanel();
        panelControls.setLayout(new BoxLayout(panelControls, BoxLayout.Y_AXIS));
        panelControls.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 1. Search Panel
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelSearch.setBorder(BorderFactory.createTitledBorder("Manual Search Control"));
        panelSearch.add(new JLabel("Name:"));
        txtSearch = new JTextField(10);
        panelSearch.add(txtSearch);
        JButton btnSearch = new JButton("Search");
        panelSearch.add(btnSearch);
        panelControls.add(panelSearch);

        // 2. Add Product Panel (Clean Grid)
        JPanel panelAdd = new JPanel(new GridBagLayout());
        panelAdd.setBorder(BorderFactory.createTitledBorder("Register New Product (IT21 OOP)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;
        
        // Product Type
        gbc.gridx = 0; gbc.gridy = row; panelAdd.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; cmbProductType = new JComboBox<>(new String[]{"Seed Product", "Equipment Product"});
        panelAdd.add(cmbProductType, gbc);
        row++;

        // ID
        gbc.gridx = 0; gbc.gridy = row; panelAdd.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1; txtId = new JTextField(12); panelAdd.add(txtId, gbc);
        row++;

        // Name
        gbc.gridx = 0; gbc.gridy = row; panelAdd.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; txtName = new JTextField(12); panelAdd.add(txtName, gbc);
        row++;

        // Price
        gbc.gridx = 0; gbc.gridy = row; panelAdd.add(new JLabel("Price (₱):"), gbc);
        gbc.gridx = 1; txtPrice = new JTextField(12); panelAdd.add(txtPrice, gbc);
        row++;

        // Stock
        gbc.gridx = 0; gbc.gridy = row; panelAdd.add(new JLabel("Stock Qty:"), gbc);
        gbc.gridx = 1; txtStock = new JTextField(12); panelAdd.add(txtStock, gbc);
        row++;

        // Extra 1
        gbc.gridx = 0; gbc.gridy = row; lblExtra1 = new JLabel("Crop Type:"); panelAdd.add(lblExtra1, gbc);
        gbc.gridx = 1; txtExtraField1 = new JTextField(12); panelAdd.add(txtExtraField1, gbc);
        row++;

        // Extra 2
        gbc.gridx = 0; gbc.gridy = row; lblExtra2 = new JLabel("Germination (%):"); panelAdd.add(lblExtra2, gbc);
        gbc.gridx = 1; txtExtraField2 = new JTextField(12); panelAdd.add(txtExtraField2, gbc);
        row++;

        // Add Button
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton btnAddProduct = new JButton("Add Product to List");
        panelAdd.add(btnAddProduct, gbc);

        panelControls.add(panelAdd);
        
        // 3. Stock Management Panel
        JPanel panelStock = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelStock.setBorder(BorderFactory.createTitledBorder("Stock Management"));
        JButton btnUpdateStock = new JButton("Update Stock (Selected Row)");
        panelStock.add(btnUpdateStock);
        panelControls.add(panelStock);

        // Put controls in a scroll pane on the right in case screen height is compact
        JScrollPane rightScrollPane = new JScrollPane(panelControls);
        rightScrollPane.setPreferredSize(new Dimension(380, 500));
        add(rightScrollPane, BorderLayout.EAST);

        // --- EVENT LISTENERS ---

        cmbProductType.addActionListener(e -> {
            if (cmbProductType.getSelectedItem().equals("Seed Product")) {
                lblExtra1.setText("Crop Type:");
                lblExtra2.setText("Germination (%):");
            } else {
                lblExtra1.setText("Equipment Type:");
                lblExtra2.setText("Warranty (Mos):");
            }
        });

        btnAddProduct.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                String name = txtName.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                String extra1 = txtExtraField1.getText().trim();
                String extra2Str = txtExtraField2.getText().trim();

                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID and Name cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product newProduct;
                if (cmbProductType.getSelectedItem().equals("Seed Product")) {
                    double germRate = Double.parseDouble(extra2Str);
                    newProduct = new SeedProduct(id, name, price, stock, extra1, germRate);
                } else {
                    int warranty = Integer.parseInt(extra2Str);
                    newProduct = new EquipmentProduct(id, name, price, stock, extra1, warranty);
                }

                inventoryList.addProduct(newProduct);
                refreshTableData();
                clearFormFields();
                JOptionPane.showMessageDialog(this, "Product successfully registered via Linked List!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Price, Stock, or metrics.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String query = txtSearch.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a product name to search.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Product result = inventoryList.searchByName(query);
            if (result != null) {
                JOptionPane.showMessageDialog(this, 
                    "Product Found!\n" +
                    "ID: " + result.getProductId() + "\n" +
                    "Name: " + result.getName() + "\n" +
                    "Price: ₱" + result.getPrice() + "\n" +
                    "Stock: " + result.getStockQuantity() + "\n" +
                    "Details: " + result.getCategoryDetails(), 
                    "Search Result - Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Product '" + query + "' was not found in the inventory list.", "Search Result - Not Found", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnUpdateStock.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row from the table first.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String targetName = (String) tableModel.getValueAt(selectedRow, 1);
            String inputNewStock = JOptionPane.showInputDialog(this, "Enter new stock quantity for " + targetName + ":");
            if (inputNewStock != null) {
                try {
                    int newStock = Integer.parseInt(inputNewStock.trim());
                    boolean success = inventoryList.updateStockByName(targetName, newStock);
                    if (success) {
                        refreshTableData();
                        JOptionPane.showMessageDialog(this, "Stock successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid stock number entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        Product[] products = inventoryList.getAllProductsArray();
        
        for (Product p : products) {
            String typeLabel = (p instanceof SeedProduct) ? "Seed" : (p instanceof EquipmentProduct) ? "Equipment" : "General";
            Object[] rowData = {
                p.getProductId(),
                p.getName(),
                typeLabel,
                p.getPrice(),
                p.getStockQuantity(),
                p.getCategoryDetails()
            };
            tableModel.addRow(rowData);
        }
    }

    private void loadSampleData() {
        inventoryList.addProduct(new SeedProduct("S001", "Hybrid Corn Seeds", 350.0, 150, "Corn", 95.0));
        inventoryList.addProduct(new SeedProduct("S002", "Red Onion Seeds", 500.0, 80, "Allium", 90.5));
        inventoryList.addProduct(new EquipmentProduct("E001", "Knapsack Sprayer", 1250.0, 25, "Manual Tool", 6));
    }

    private void clearFormFields() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtExtraField1.setText("");
        txtExtraField2.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryDashboard().setVisible(true));
    }
}