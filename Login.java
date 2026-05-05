import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login {
}

class LoginFrame {
    LoginFrame() {
        JFrame frame = new JFrame("Condo System Login");
        frame.setSize(520, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIUtils.PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel titleLabel = new JLabel("Condo Management");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel formPanel = UIUtils.createFormPanel();
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel userLabel = UIUtils.createStyledLabel("Username");
        JTextField user = UIUtils.createStyledTextField();
        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setBackground(UIUtils.BACKGROUND);
        userPanel.setMaximumSize(new Dimension(500, 60));
        userPanel.add(userLabel, BorderLayout.NORTH);
        userPanel.add(user, BorderLayout.CENTER);

        JLabel passLabel = UIUtils.createStyledLabel("Password");
        JPasswordField pass = UIUtils.createStyledPasswordField();
        JPanel passPanel = new JPanel(new BorderLayout(10, 0));
        passPanel.setBackground(UIUtils.BACKGROUND);
        passPanel.setMaximumSize(new Dimension(500, 60));
        passPanel.add(passLabel, BorderLayout.NORTH);
        passPanel.add(pass, BorderLayout.CENTER);

        formPanel.add(userPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passPanel);
        formPanel.add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(UIUtils.PANEL_BACKGROUND);
        buttonPanel.setMaximumSize(new Dimension(500, 50));

        JButton register = UIUtils.createSecondaryButton("Register");
        JButton login = UIUtils.createStyledButton("Login");

        buttonPanel.add(register);
        buttonPanel.add(login);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalGlue());

        login.addActionListener(e -> {
            String username = user.getText().trim();
            String password = new String(pass.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username and password",
                        "Login Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (AuthUtils.authenticateUser(username, password)) {
                    frame.dispose();
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Login failed: " + ex.getMessage(),
                        "Login Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        register.addActionListener(e -> {
            frame.dispose();
            new RegisterFrame();
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}

class RegisterFrame {
    RegisterFrame() {
        JFrame frame = new JFrame("Create New Account");
        frame.setSize(520, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIUtils.PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel titleLabel = new JLabel("Register Account");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel formPanel = UIUtils.createFormPanel();
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel userLabel = UIUtils.createStyledLabel("Username");
        JTextField user = UIUtils.createStyledTextField();
        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setBackground(UIUtils.BACKGROUND);
        userPanel.setMaximumSize(new Dimension(500, 60));
        userPanel.add(userLabel, BorderLayout.NORTH);
        userPanel.add(user, BorderLayout.CENTER);

        JLabel passLabel = UIUtils.createStyledLabel("Password");
        JPasswordField pass = UIUtils.createStyledPasswordField();
        JPanel passPanel = new JPanel(new BorderLayout(10, 0));
        passPanel.setBackground(UIUtils.BACKGROUND);
        passPanel.setMaximumSize(new Dimension(500, 60));
        passPanel.add(passLabel, BorderLayout.NORTH);
        passPanel.add(pass, BorderLayout.CENTER);

        JLabel confirmLabel = UIUtils.createStyledLabel("Confirm Password");
        JPasswordField confirm = UIUtils.createStyledPasswordField();
        JPanel confirmPanel = new JPanel(new BorderLayout(10, 0));
        confirmPanel.setBackground(UIUtils.BACKGROUND);
        confirmPanel.setMaximumSize(new Dimension(500, 60));
        confirmPanel.add(confirmLabel, BorderLayout.NORTH);
        confirmPanel.add(confirm, BorderLayout.CENTER);

        formPanel.add(userPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(confirmPanel);
        formPanel.add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(UIUtils.PANEL_BACKGROUND);
        buttonPanel.setMaximumSize(new Dimension(500, 50));

        JButton back = UIUtils.createSecondaryButton("Back to Login");
        JButton submit = UIUtils.createStyledButton("Register");

        buttonPanel.add(back);
        buttonPanel.add(submit);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalGlue());

        submit.addActionListener(e -> {
            String username = user.getText().trim();
            String password = new String(pass.getPassword());
            String confirmPassword = new String(confirm.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Registration Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Registration Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (AuthUtils.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Account registered successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new LoginFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists or registration failed.",
                            "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Registration failed: " + ex.getMessage(),
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        back.addActionListener(e -> {
            frame.dispose();
            new LoginFrame();
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}