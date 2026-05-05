import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class viewMaintenance {

    viewMaintenance() {
        JFrame frame = new JFrame("View Maintenance Requests");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        mainPanel.add(UIUtils.createHeaderPanel("Maintenance Requests"), BorderLayout.NORTH);

        String[] cols = {"ID", "Unit ID", "Issue", "Date", "Status", "Cost"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        table.setFont(UIUtils.LABEL_FONT);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(UIUtils.PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);
                c.setBackground(isSelected ? UIUtils.PRIMARY_COLOR : Color.WHITE);
                return c;
            }
        });
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setFont(UIUtils.HEADER_FONT);
        header.setReorderingAllowed(false);

        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM maintenance");

            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaintenanceID"),
                    rs.getInt("UnitID"),
                    rs.getString("IssueDescription"),
                    rs.getDate("RequestDate"),
                    rs.getString("Status"),
                    String.format("$%.2f", rs.getDouble("Cost"))
                });
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}