package com.agrinexus.ui;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.chrono.JapaneseChronology;
import java.sql.*;
import javax.swing.*;

public class loginfrom  extends JFrame{
    final private Font mainFont = new Font ("segoe print" , Font.BOLD,18);
    JTextField tfEmail; 
    JPasswordField pfPassword;

        public void initialize () {
 /*  -----------    form pannel     ---------------------- */
        Jlable lbLoginForm = new JLabel("Login Form", SwingConstants.CENTRE);
        lbLoginForm.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password")
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));

        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEamil);
        formPanel.add(lbPassword);
        formPanel.add(tfPassword);
/* ---------button panel--------------- */
     JButton btnLogin = new JButton("Login");
     btnLogin.setFont(mainFont);
     btnLogin.addActionListener(new ActionListner(){
        @Override
        public void  actionPerformed(ActionEvent e){
            String Email = tfEmail.getText();
            String Password = String.valueOf(pfPassword.getPassword());

            User user = getAutheticatedUser(Email, Password)
            if (User=!null ){
                MainFrame mainFrame= new mainFrame();
                mainFrame.initalize(user);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(LoginForm.this, "Email or Password Invalid",
                "try again",
                JOptionPane.ERROR_MESSAGE);
            }
        }


     });
     JButton btnCancel = new JButton("Cancel");
     btnCancel.setFont(mainFont);
     btnCancel.addActionListenet(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose()
        }
     });
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
     buttonPanel.add(btnLogin);
     buttonPanel.add(btnCancel);


/* ----------------initialize frame ------------ */


        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);








            setTitle("Login Form");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setSize(400,500);
            setMinimumSize(new Dimension(350,450));
            setLocationRelativeTo(null);
            setVisible(true);

    }
    private User getAutheticatedUser(String Email, String password){
        User user = null ;

        final String DB_URL = "http://localhost/phpmyadmin/index.php?route=/table/change&db=aggrinexususers&table=users " /*url that connects us to dtabase */
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            connection conn =   DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql= "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.preparedStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new user();
                user.name = resultSet.getString("name");
                user.phone = resultSet.getString("phone");
                user.email = resultSet.getString("email");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            preparedStatement.close();
            conn.close();
        }
        catch(Exception e ){
            System.out.println("Database connection failed");
        }
        return user ;
    }
}
