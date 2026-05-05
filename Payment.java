import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Payment {

    Payment() {
        JFrame frame = new JFrame("Add Payment");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Record Payment"), BorderLayout.NORTH);

        JPanel formPanel = UIUtils.createFormPanel();

        JTextField unitId = UIUtils.createStyledTextField();
        JTextField tenantId = UIUtils.createStyledTextField();
        JTextField amount = UIUtils.createStyledTextField();
        JTextField date = UIUtils.createStyledTextField();

        UIUtils.addFormRow(formPanel, "Unit ID *", unitId);
        UIUtils.addFormRow(formPanel, "Tenant ID *", tenantId);
        UIUtils.addFormRow(formPanel, "Amount *", amount);
        UIUtils.addFormRow(formPanel, "Payment Date (YYYY-MM-DD) *", date);

        formPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton save = UIUtils.createStyledButton("Save Payment");
        JButton cancel = UIUtils.createSecondaryButton("Cancel");

        save.addActionListener(e -> {
            if (unitId.getText().isEmpty() || tenantId.getText().isEmpty() || 
                amount.getText().isEmpty() || date.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();

                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO payment(UnitID, TenantID, PaymentDate, Amount, Status) VALUES (?, ?, ?, ?, 'Paid')"
                );

                ps.setInt(1, Integer.parseInt(unitId.getText()));
                ps.setInt(2, Integer.parseInt(tenantId.getText()));
                ps.setString(3, date.getText());
                ps.setDouble(4, Double.parseDouble(amount.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Payment saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for IDs and Amount", 
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