package bt_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class LoginRegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private Connection conn;
    private Statement statement;
    
    private static String DB_URL = "jdbc:mysql://localhost:3306/javanc";
	private static String USER_NAME = "root";
	private static String PASSWORD = "";

    public LoginRegistrationForm() {
        super("Login / Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        // Khởi tạo các trường và nút đăng nhập, đăng ký
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        // Sự kiện khi nhấn nút đăng nhập
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Sự kiện khi nhấn nút đăng ký
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        add(panel);
        setVisible(true);

        // Kết nối đến cơ sở dữ liệu
        
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        	System.out.println("tk");
            statement = conn.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Phương thức đăng nhập
 // Login method
 // Login method
    private void login() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String hashedPassword = hashPassword(password); // Mã hoá mật khẩu đầu vào

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (storedPassword.equals(hashedPassword)) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username + "!");
                    // Thay đổi giao diện của cửa sổ hiện tại
                    setVisible(false); // Ẩn cửa sổ đăng nhập
                    showWelcomeScreen(); // Hiển thị giao diện welcome
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed. Invalid username or password.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Hiển thị giao diện welcome
    private void showWelcomeScreen() {
        JFrame welcomeFrame = new JFrame("Welcome");
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(300, 150);
        welcomeFrame.setLocationRelativeTo(null);
        
        // Thêm các thành phần của giao diện welcome vào đây
        
        welcomeFrame.setVisible(true);
    }


    // Registration method
    private void register() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String hashedPassword = hashPassword(password);

        try {
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('" + username + "', '" + hashedPassword + "')");
            JOptionPane.showMessageDialog(this, "Registration successful! Welcome, " + username + "!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Hash password method
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginRegistrationForm();
            }
        });
    }
}
