import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Property {
    Property() {
        JFrame frame = new JFrame("Add Property");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Add New Property"), BorderLayout.NORTH);

        JPanel formPanel = UIUtils.createFormPanel();

        JTextField name = UIUtils.createStyledTextField();
        JTextField location = UIUtils.createStyledTextField();

        UIUtils.addFormRow(formPanel, "Property Name *", name);
        UIUtils.addFormRow(formPanel, "Location *", location);

        formPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton save = UIUtils.createStyledButton("Save Property");
        JButton cancel = UIUtils.createSecondaryButton("Cancel");

        save.addActionListener(e -> {
            if (name.getText().isEmpty() || location.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO property(PropertyName, Location) VALUES (?, ?)"
                );
                ps.setString(1, name.getText());
                ps.setString(2, location.getText());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(frame, "Property saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancel.addActionListener(e -> frame.dispose());

        buttonPanel.add(save);
        buttonPanel.add(cancel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIUtils.BACKGROUND);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}