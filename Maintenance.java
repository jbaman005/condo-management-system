import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class Maintenance {

    Maintenance() {
        JFrame frame = new JFrame("Add Maintenance Request");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Create Maintenance Request"), BorderLayout.NORTH);

        JPanel formPanel = UIUtils.createFormPanel();

        JTextField unitId = UIUtils.createStyledTextField();
        JTextField issue = UIUtils.createStyledTextField();
        JTextField cost = UIUtils.createStyledTextField();
        JTextField date = UIUtils.createStyledTextField();

        UIUtils.addFormRow(formPanel, "Unit ID *", unitId);
        UIUtils.addFormRow(formPanel, "Issue Description *", issue);
        UIUtils.addFormRow(formPanel, "Cost *", cost);
        UIUtils.addFormRow(formPanel, "Request Date (YYYY-MM-DD) *", date);

        formPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton save = UIUtils.createStyledButton("Save Request");
        JButton cancel = UIUtils.createSecondaryButton("Cancel");

        save.addActionListener(e -> {
            if (unitId.getText().isEmpty() || issue.getText().isEmpty() || 
                cost.getText().isEmpty() || date.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO maintenance(UnitID, IssueDescription, RequestDate, Status, Cost) VALUES (?, ?, ?, 'Pending', ?)"
                );

                ps.setInt(1, Integer.parseInt(unitId.getText()));
                ps.setString(2, issue.getText());
                ps.setString(3, date.getText());
                ps.setDouble(4, Double.parseDouble(cost.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Maintenance Request saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Unit ID and Cost", 
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