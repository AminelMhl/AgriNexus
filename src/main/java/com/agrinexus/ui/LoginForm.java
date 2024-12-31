package com.agrinexus.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginForm extends JFrame {
    final private Font mainFont = new Font("segoe print", Font.BOLD, 18);
    JTextField tfEmail;
    JPasswordField pfPassword;
    public void initialize() {
        /* ----------- Form Panel ---------------------- */
        JLabel lbLoginForm = new JLabel("Login Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);
        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);
        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);
        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);
        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        /* --------- Button Panel --------------- */
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAuthenticatedUser(email, password);
                if (user != null) {
                    mainFrame mainFrame = new mainFrame(user);
                    mainFrame.initalize(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Email or Password Invalid",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton btnSignup = new JButton("Sign Up");
        btnSignup.setFont(mainFont);
        btnSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupForm();
            }
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignup);
        buttonPanel.add(btnCancel);
        /* ---------------- Initialize Frame ------------ */
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private User getAuthenticatedUser(String email, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashPassword(password));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.phone = resultSet.getString("phone");
                user.email = resultSet.getString("email");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return user;
    }
    private void showSignupForm() {
        JDialog signupDialog = new JDialog(this, "Sign Up", true);
        signupDialog.setLayout(new GridLayout(0, 1, 10, 10));
        signupDialog.setSize(400, 600);
        signupDialog.setLocationRelativeTo(this);
        JLabel lbSignupForm = new JLabel("Sign Up Form", SwingConstants.CENTER);
        lbSignupForm.setFont(mainFont);
        JLabel lbName = new JLabel("Name");
        lbName.setFont(mainFont);
        JTextField tfName = new JTextField();
        tfName.setFont(mainFont);
        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);
        JTextField tfEmailSignup = new JTextField();
        tfEmailSignup.setFont(mainFont);
        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);
        JPasswordField pfPasswordSignup = new JPasswordField();
        pfPasswordSignup.setFont(mainFont);
        JLabel lbConfirmPassword = new JLabel("Confirm Password");
        lbConfirmPassword.setFont(mainFont);
        JPasswordField pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setFont(mainFont);
        JLabel lbPhone = new JLabel("Phone Number");
        lbPhone.setFont(mainFont);
        JTextField tfPhone = new JTextField();
        tfPhone.setFont(mainFont);
        JLabel lbAddress = new JLabel("Address");
        lbAddress.setFont(mainFont);
        JTextField tfAddress = new JTextField();
        tfAddress.setFont(mainFont);
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setFont(mainFont);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String email = tfEmailSignup.getText();
                String password = String.valueOf(pfPasswordSignup.getPassword());
                String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
                String phone = tfPhone.getText();
                String address = tfAddress.getText();

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(signupDialog, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (doesEmailExist(email)) {
                    JOptionPane.showMessageDialog(signupDialog, "Email already exists. Please choose another email.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (doesPasswordExist(password)) {
                    JOptionPane.showMessageDialog(signupDialog, "Please choose another password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (addUserToDatabase(name, email, hashPassword(password), phone, address)) {
                    JOptionPane.showMessageDialog(signupDialog, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    signupDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupDialog, "Sign up failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signupDialog.add(lbSignupForm);
        signupDialog.add(lbName);
        signupDialog.add(tfName);
        signupDialog.add(lbEmail);
        signupDialog.add(tfEmailSignup);
        signupDialog.add(lbPassword);
        signupDialog.add(pfPasswordSignup);
        signupDialog.add(lbConfirmPassword);
        signupDialog.add(pfConfirmPassword);
        signupDialog.add(lbPhone);
        signupDialog.add(tfPhone);
        signupDialog.add(lbAddress);
        signupDialog.add(tfAddress);
        signupDialog.add(btnSubmit);
        signupDialog.setVisible(true);
    }
    private boolean addUserToDatabase(String name, String email, String password, String phone, String address) {
        final
        String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Error adding user to database: " + e.getMessage());
            return false;
        }
    }
    private boolean doesEmailExist(String email) {
        final String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT email FROM users WHERE email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return exists;
        } catch (Exception e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false;
        }
    }
    private boolean doesPasswordExist(String password) {
        final String DB_URL = "jdbc:mysql://localhost:3306/aggrinexususers";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT password FROM users WHERE password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, hashPassword(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return exists;
        } catch (Exception e) {
            System.out.println("Error checking password existence: " + e.getMessage());
            return false;
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
    }
}
