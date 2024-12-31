
 package com.agrinexus.ui;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
 public class mainFrame extends JFrame {
     private final User User;
 
     public mainFrame(User user) {
         this.User = user;
         initialize();
     }
 
     private void initialize() {
         // Dark theme colors
         Color backgroundColor = new Color(28, 30, 40);
         Color accentColor = new Color(114, 137, 218);
         Color textColor = new Color(220, 221, 222);
 
         setTitle("Welcome");
         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         setSize(500, 400);
         setLocationRelativeTo(null);
 
         getContentPane().setBackground(backgroundColor);
         setLayout(new BorderLayout());
 
         JLabel lbWelcome = new JLabel("Welcome, " + User.name, SwingConstants.CENTER);
         lbWelcome.setFont(new Font("Roboto", Font.BOLD, 24));
         lbWelcome.setForeground(textColor);
 
         JButton btnCreateFarmer = createStyledButton("Create Farmer Account", accentColor, textColor);
         btnCreateFarmer.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 showFarmerForm();
             }
         });
 
         JButton btnManageFarms = createStyledButton("Manage Farms", accentColor, textColor);
         btnManageFarms.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new FarmManagementFrame(User);
                 dispose();
             }
         });
 
         JPanel buttonPanel = new JPanel();
         buttonPanel.setBackground(backgroundColor);
         buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
         buttonPanel.add(btnCreateFarmer);
         buttonPanel.add(btnManageFarms);
 
         add(lbWelcome, BorderLayout.NORTH);
         add(buttonPanel, BorderLayout.CENTER);
 
         setVisible(true);
     }
 
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
 
     private void showFarmerForm() {
        JDialog farmerDialog = new JDialog(this, "Create Farmer Account", true);
        farmerDialog.setLayout(new GridLayout(0, 1, 10, 10));
        farmerDialog.setSize(400, 300);
        farmerDialog.setLocationRelativeTo(this);
    
        JTextField tfFirstName = new JTextField();
        JTextField tfLastName = new JTextField();
        JTextField tfCity = new JTextField();
    
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(_ -> {
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String city = tfCity.getText();
    
            Farmer farmer = createFarmerAccount(User, firstName, lastName, city);
            if (farmer != null) {
                JOptionPane.showMessageDialog(this, 
                    "Farmer account created successfully!\nFarmer ID: " + farmer.getFarmerId());
                farmerDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to create farmer account.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        farmerDialog.add(new JLabel("First Name"));
        farmerDialog.add(tfFirstName);
        farmerDialog.add(new JLabel("Last Name"));
        farmerDialog.add(tfLastName);
        farmerDialog.add(new JLabel("City"));
        farmerDialog.add(tfCity);
        farmerDialog.add(btnSubmit);
    
        farmerDialog.setVisible(true);
    }
    
 
 
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
        

    public void initalize(User user2) {
        
        throw new UnsupportedOperationException("Unimplemented method 'initalize'");
    }
 }
 
