package com.agrinexus.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class mainFrame extends JFrame {
    private final User User;

    public mainFrame(User user) {
        this.User = user;
        initialize();
    }

    private void initialize() {
        // Define dark theme colors
        Color backgroundColor = new Color(28, 30, 40);
        Color accentColor = new Color(114, 137, 218);
        Color textColor = new Color(220, 221, 222);

        setTitle("Welcome");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        // Welcome label
        JLabel lbWelcome = new JLabel("Welcome, " + User.name, SwingConstants.CENTER);
        lbWelcome.setFont(new Font("Roboto", Font.BOLD, 24));
        lbWelcome.setForeground(textColor);

        // Create Farmer Account button
        JButton btnCreateFarmer = createStyledButton("Create Farmer Account", accentColor, textColor);

        // Manage Farms button, initially disabled
        JButton btnManageFarms = createStyledButton("Manage Farms", accentColor, textColor);
        btnManageFarms.setEnabled(false); // Disabled initially

        // Action for Create Farmer button
        btnCreateFarmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Farmer creation form
                JDialog farmerDialog = new JDialog(mainFrame.this, "Create Farmer Account", true);
                farmerDialog.setLayout(new GridLayout(0, 1, 10, 10));
                farmerDialog.setSize(400, 300);
                farmerDialog.setLocationRelativeTo(mainFrame.this);

                // Input fields for Farmer details
                JTextField tfFirstName = new JTextField();
                JTextField tfLastName = new JTextField();
                JTextField tfCity = new JTextField();

                // Submit button for farmer form
                JButton btnSubmit = new JButton("Submit");
                btnSubmit.addActionListener(_ -> {
                    String firstName = tfFirstName.getText();
                    String lastName = tfLastName.getText();
                    String city = tfCity.getText();

                    Farmer farmer = createFarmerAccount(User, firstName, lastName, city);
                    if (farmer != null) {
                        // Success message
                        JOptionPane.showMessageDialog(mainFrame.this,
                            "Farmer account created successfully!\nFarmer ID: " + farmer.getFarmerId(),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                        farmerDialog.dispose(); // Close the farmer form dialog
                        btnManageFarms.setEnabled(true); // Enable Manage Farms button
                    } else {
                        // Error message
                        JOptionPane.showMessageDialog(mainFrame.this,
                            "Failed to create farmer account.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Add input fields and submit button to the dialog
                farmerDialog.add(new JLabel("First Name"));
                farmerDialog.add(tfFirstName);
                farmerDialog.add(new JLabel("Last Name"));
                farmerDialog.add(tfLastName);
                farmerDialog.add(new JLabel("City"));
                farmerDialog.add(tfCity);
                farmerDialog.add(btnSubmit);

                farmerDialog.setVisible(true);
            }
        });

        // Action for Manage Farms button
        btnManageFarms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Farm creation form
                showFarmForm();
            }
        });

        // Layout for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(btnCreateFarmer);
        buttonPanel.add(btnManageFarms);

        // Add components to the main frame
        add(lbWelcome, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to create a styled JButton
    private JButton createStyledButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 16));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    // Method to create a farmer account in the database
    private Farmer createFarmerAccount(User user, String firstName, String lastName, String city) {
        final String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO farmers (name, lastName, city) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, city);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int farmerId = rs.getInt(1); // Assuming the database auto-generates IDs
                    Farmer farmer = new Farmer(farmerId, firstName + " " + lastName);
                    stmt.close();
                    conn.close();
                    return farmer;
                }
            }

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error creating farmer account: " + ex.getMessage());
        }
        return null;
    }

    // Method to show the farm creation form
    private void showFarmForm() {
        JDialog farmDialog = new JDialog(this, "Create Farm", true);
        farmDialog.setLayout(new GridLayout(0, 1, 10, 10));
        farmDialog.setSize(400, 400);
        farmDialog.setLocationRelativeTo(this);

        JTextField tfFarmLength = new JTextField();
        JTextField tfFarmWidth = new JTextField();

        // A list to display added fields
        DefaultListModel<String> fieldListModel = new DefaultListModel<>();
        JList<String> fieldList = new JList<>(fieldListModel);

        // Button to add a field to the farm
        JButton btnAddField = new JButton("Add Field");
        btnAddField.addActionListener(_ -> {
            String fieldLengthStr = JOptionPane.showInputDialog("Enter Field Length:");
            String fieldWidthStr = JOptionPane.showInputDialog("Enter Field Width:");
            String crop = JOptionPane.showInputDialog("Enter Crop:");

            try {
                int fieldLength = Integer.parseInt(fieldLengthStr);
                int fieldWidth = Integer.parseInt(fieldWidthStr);

                int farmLength = Integer.parseInt(tfFarmLength.getText());
                int farmWidth = Integer.parseInt(tfFarmWidth.getText());

                if (fieldLength <= farmLength && fieldWidth <= farmWidth) {
                    fieldListModel.addElement("Field (" + fieldLength + "x" + fieldWidth + ") - Crop: " + crop);
                } else {
                    JOptionPane.showMessageDialog(this, "Field dimensions exceed farm dimensions!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for dimensions!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Button to submit the farm details
        JButton btnSubmitFarm = new JButton("Submit Farm");
        btnSubmitFarm.addActionListener(_ -> {
            try {
                int farmLength = Integer.parseInt(tfFarmLength.getText());
                int farmWidth = Integer.parseInt(tfFarmWidth.getText());

                // Save farm and fields to the database
                saveFarm(User, farmLength, farmWidth, fieldListModel);

                JOptionPane.showMessageDialog(this, "Farm created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                farmDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for farm dimensions!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adding components to the dialog
        farmDialog.add(new JLabel("Farm Length"));
        farmDialog.add(tfFarmLength);
        farmDialog.add(new JLabel("Farm Width"));
        farmDialog.add(tfFarmWidth);
        farmDialog.add(new JLabel("Fields:"));
        farmDialog.add(new JScrollPane(fieldList));
        farmDialog.add(btnAddField);
        farmDialog.add(btnSubmitFarm);

        farmDialog.setVisible(true);
    }

    // Method to save farm and fields to the database
    private void saveFarm(User user, int farmLength, int farmWidth, DefaultListModel<String> fieldListModel) {
        final String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            // Insert farm details into the database
            String farmSql = "INSERT INTO farms (farmer_id, length, width) VALUES (?, ?, ?)";
            PreparedStatement farmStmt = conn.prepareStatement(farmSql, PreparedStatement.RETURN_GENERATED_KEYS);
            farmStmt.setInt(1, user.getUserId()); // Assuming user has a method getId()
            farmStmt.setInt(2, farmLength);
            farmStmt.setInt(3, farmWidth);
            farmStmt.executeUpdate();

            // Retrieve the farm ID
            var farmRs = farmStmt.getGeneratedKeys();
            if (farmRs.next()) {
                int farmId = farmRs.getInt(1);

                // Insert fields into the database
                for (int i = 0; i < fieldListModel.getSize(); i++) {
                    String fieldData = fieldListModel.get(i);
                    String[] fieldDetails = fieldData.split(" - ");
                    String[] dimensions = fieldDetails[0].replace("Field (", "").replace(")", "").split("x");
                    int fieldLength = Integer.parseInt(dimensions[0]);
                    int fieldWidth = Integer.parseInt(dimensions[1]);
                    String crop = fieldDetails[1].replace("Crop: ", "");

                    String fieldSql = "INSERT INTO fields (farm_id, length, width, crop) VALUES (?, ?, ?, ?)";
                    PreparedStatement fieldStmt = conn.prepareStatement(fieldSql);
                    fieldStmt.setInt(1, farmId);
                    fieldStmt.setInt(2, fieldLength);
                    fieldStmt.setInt(3, fieldWidth);
                    fieldStmt.setString(4, crop);
                    fieldStmt.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error saving farm or fields: " + ex.getMessage());
        }
    }

    public void initalize(User user2) {
        // TODO Auto-generated method stub
        
    }
}
