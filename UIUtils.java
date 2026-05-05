import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIUtils {
    public static final Color PRIMARY_COLOR = new Color(7, 81, 65);
    public static final Color PRIMARY_DARK = new Color(5, 53, 43);
    public static final Color ACCENT_COLOR = new Color(28, 142, 111);
    public static final Color BACKGROUND = new Color(34, 38, 43);
    public static final Color PANEL_BACKGROUND = new Color(45, 52, 58);
    public static final Color TEXT_COLOR = new Color(235, 239, 242);
    public static final Color LABEL_COLOR = new Color(189, 195, 199);

    public static final Font TITLE_FONT = new Font("JetBrains Mono", Font.BOLD, 26);
    public static final Font HEADER_FONT = new Font("JetBrains Mono", Font.BOLD, 16);
    public static final Font BUTTON_FONT = new Font("JetBrains Mono", Font.PLAIN, 14);
    public static final Font LABEL_FONT = new Font("JetBrains Mono", Font.PLAIN, 13);
    public static final Font INPUT_FONT = new Font("JetBrains Mono", Font.PLAIN, 13);

    private static class RoundedButton extends JButton {
        private final int arc;
        private Color borderColor;
        private int borderWidth;

        RoundedButton(String text, int arc) {
            super(text);
            this.arc = arc;
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setFont(BUTTON_FONT);
            setMargin(new Insets(12, 20, 12, 20));
            this.borderWidth = 0;
        }

        void setRoundedBorder(Color color, int width) {
            this.borderColor = color;
            this.borderWidth = width;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            if (borderColor != null && borderWidth > 0) {
                g2.setStroke(new BasicStroke(borderWidth));
                g2.setColor(borderColor);
                g2.drawRoundRect(borderWidth / 2, borderWidth / 2,
                    getWidth() - borderWidth, getHeight() - borderWidth, arc, arc);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static RoundedButton createBaseButton(String text) {
        RoundedButton button = new RoundedButton(text, 30);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setBorderPainted(false);
        return button;
    }

    public static JButton createStyledButton(String text) {
        RoundedButton button = createBaseButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    public static JButton createSecondaryButton(String text) {
        RoundedButton button = createBaseButton(text);
        button.setBackground(new Color(62, 69, 76));
        button.setForeground(TEXT_COLOR);
        button.setRoundedBorder(new Color(80, 88, 97), 2);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 79, 87));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(62, 69, 76));
            }
        });

        return button;
    }

    public static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(INPUT_FONT);
        field.setBackground(new Color(58, 63, 69));
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 88, 97), 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        field.setPreferredSize(new Dimension(320, 40));
        return field;
    }

    public static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(INPUT_FONT);
        field.setBackground(new Color(58, 63, 69));
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 88, 97), 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        field.setPreferredSize(new Dimension(320, 40));
        return field;
    }

    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }

    public static JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    public static void styleFrame(JFrame frame) {
        frame.setBackground(BACKGROUND);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static void addFormRow(JPanel formPanel, String labelText, JComponent field) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(PANEL_BACKGROUND);
        rowPanel.setMaximumSize(new Dimension(500, 40));
        
        JLabel label = createStyledLabel(labelText);
        label.setPreferredSize(new Dimension(120, 35));
        
        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);
        
        formPanel.add(rowPanel);
        formPanel.add(Box.createVerticalStrut(10));
    }

    public static JPanel createButtonGrid(int columns) {
        JPanel panel = new JPanel(new GridLayout(0, columns, 15, 15));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static JButton createDashboardButton(String title, String icon) {
        String html = "<html><div style='text-align:center;'>"
            + "<div style='font-size:34px; margin-bottom:10px; line-height:1.1;'>" + icon + "</div>"
            + "<div style='font-size:14px; color:#EBEFF2; font-weight:600;'>" + title + "</div>"
            + "</div></html>";

        RoundedButton button = createBaseButton(html);
        button.setPreferredSize(new Dimension(170, 140));
        button.setFont(HEADER_FONT);
        button.setRoundedBorder(new Color(0, 0, 0, 0), 0);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }
}
