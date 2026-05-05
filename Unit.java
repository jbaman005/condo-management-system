import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Unit {
    Unit() {
        JFrame frame = new JFrame("Add Unit");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Add New Unit"), BorderLayout.NORTH);

        JPanel formPanel = UIUtils.createFormPanel();

        JTextField propertyId = UIUtils.createStyledTextField();
        JTextField unitNumber = UIUtils.createStyledTextField();
        JTextField floor = UIUtils.createStyledTextField();
        JTextField rent = UIUtils.createStyledTextField();

        UIUtils.addFormRow(formPanel, "Property ID *", propertyId);
        UIUtils.addFormRow(formPanel, "Unit Number *", unitNumber);
        UIUtils.addFormRow(formPanel, "Floor Number *", floor);
        UIUtils.addFormRow(formPanel, "Monthly Rent *", rent);

        formPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton save = UIUtils.createStyledButton("Save Unit");
        JButton cancel = UIUtils.createSecondaryButton("Cancel");

        save.addActionListener(e -> {
            if (propertyId.getText().isEmpty() || unitNumber.getText().isEmpty() || 
                floor.getText().isEmpty() || rent.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO unit(PropertyID, UnitNumber, Floor, Status, MonthlyRent) VALUES (?, ?, ?, 'Vacant', ?)"
                );
                ps.setInt(1, Integer.parseInt(propertyId.getText()));
                ps.setString(2, unitNumber.getText());
                ps.setInt(3, Integer.parseInt(floor.getText()));
                ps.setDouble(4, Double.parseDouble(rent.getText()));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Unit saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for ID, Floor, and Rent", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
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
