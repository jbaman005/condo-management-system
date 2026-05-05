import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

class Tenant {
    Tenant() {
        JFrame frame = new JFrame("Add Tenant");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Add New Tenant"), BorderLayout.NORTH);

        JPanel formPanel = UIUtils.createFormPanel();

        JLabel nameLabel = UIUtils.createStyledLabel("Full Name *");
        JTextField nameField = UIUtils.createStyledTextField();
        
        JLabel contactLabel = UIUtils.createStyledLabel("Contact Number *");
        JTextField contactField = UIUtils.createStyledTextField();
        
        JLabel emailLabel = UIUtils.createStyledLabel("Email (Optional)");
        JTextField emailField = UIUtils.createStyledTextField();

        UIUtils.addFormRow(formPanel, "Full Name *", nameField);
        UIUtils.addFormRow(formPanel, "Contact *", contactField);
        UIUtils.addFormRow(formPanel, "Email", emailField);

        formPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton saveBtn = UIUtils.createStyledButton("Save Tenant");
        JButton cancelBtn = UIUtils.createSecondaryButton("Cancel");

        saveBtn.addActionListener(e -> {
            if (nameField.getText().isEmpty() || contactField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO tenant(FullName, ContactNumber, Email) VALUES (?, ?, ?)"
                );
                ps.setString(1, nameField.getText());
                ps.setString(2, contactField.getText());
                String email = emailField.getText().trim();
                if (email.isEmpty()) {
                    ps.setNull(3, Types.VARCHAR);
                } else {
                    ps.setString(3, email);
                }
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(frame, "Tenant saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> frame.dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIUtils.BACKGROUND);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}