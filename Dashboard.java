import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class Dashboard {
    private JFrame frame;

    Dashboard() {
        frame = new JFrame("Condo Management - Dashboard");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        UIUtils.styleFrame(frame);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIUtils.PRIMARY_DARK);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Welcome to Condo Management System");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(UIUtils.TEXT_COLOR);

        JButton logoutBtn = UIUtils.createSecondaryButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new LoginFrame();
        });

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIUtils.BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonGrid = UIUtils.createButtonGrid(5);

        JButton viewTenant = UIUtils.createDashboardButton("View Tenants", "👥");
        JButton viewUnit = UIUtils.createDashboardButton("View Units", "🚪");
        JButton viewPayment = UIUtils.createDashboardButton("View Payments", "💳");
        JButton viewMaintenance = UIUtils.createDashboardButton("View Maintenance", "🔧");
        JButton viewProperty = UIUtils.createDashboardButton("View Properties", "🏠");

        JButton tenant = UIUtils.createDashboardButton("Add Tenant", "👥");
        JButton unit = UIUtils.createDashboardButton("Add Unit", "🚪");
        JButton payment = UIUtils.createDashboardButton("Add Payment", "💳");
        JButton maintenance = UIUtils.createDashboardButton("Add Maintenance", "🔧");
        JButton property = UIUtils.createDashboardButton("Add Property", "🏠");

        buttonGrid.add(viewTenant);
        buttonGrid.add(viewUnit);
        buttonGrid.add(viewPayment);
        buttonGrid.add(viewMaintenance);
        buttonGrid.add(viewProperty);
        buttonGrid.add(tenant);
        buttonGrid.add(unit);
        buttonGrid.add(payment);
        buttonGrid.add(maintenance);
        buttonGrid.add(property);

        JScrollPane scrollPane = new JScrollPane(buttonGrid);
        scrollPane.setBackground(UIUtils.BACKGROUND);
        scrollPane.setBorder(null);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        tenant.addActionListener(e -> new Tenant());
        payment.addActionListener(e -> new Payment());
        maintenance.addActionListener(e -> new Maintenance());
        property.addActionListener(e -> new Property());
        unit.addActionListener(e -> new Unit());
        viewTenant.addActionListener(e -> new viewTenant());
        viewUnit.addActionListener(e -> new viewUnit());
        viewPayment.addActionListener(e -> new viewPayment());
        viewMaintenance.addActionListener(e -> new viewMaintenance());
        viewProperty.addActionListener(e -> new viewProperty());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}