package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class UIUtils {

    // Color constants
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    public static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    public static final Color ACCENT_COLOR = new Color(16, 185, 129);
    public static final Color PRIMARY_HOVER = new Color(37, 99, 235);
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    public static final Color WARNING_COLOR = new Color(245, 158, 11);
    public static final Color DANGER_COLOR = new Color(239, 68, 68);
    public static final Color BACKGROUND_COLOR = new Color(249, 250, 251);
    public static final Color CARD_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(17, 24, 39);
    public static final Color TEXT_SECONDARY = new Color(75, 85, 99);
    public static final Color BORDER_COLOR = new Color(229, 231, 235);

    // Font constants
    public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font LARGE_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    // Dimension constants
    public static final Dimension BUTTON_SIZE = new Dimension(100, 36);
    public static final Dimension INPUT_SIZE = new Dimension(200, 36);
    public static final Insets DEFAULT_INSETS = new Insets(8, 8, 8, 8);
    public static final Insets PADDING_SMALL = new Insets(8, 12, 8, 12);
    public static final Insets PADDING_MEDIUM = new Insets(12, 16, 12, 16);
    public static final Insets PADDING_LARGE = new Insets(16, 20, 16, 20);

    private UIUtils() {
    }

    public static void styleButton(JButton button, ButtonStyle style) {
        Color bg, hover, fg;

        switch (style) {
            case PRIMARY:
                bg = PRIMARY_COLOR;
                hover = PRIMARY_HOVER;
                fg = Color.WHITE;
                break;
            case SUCCESS:
                bg = SUCCESS_COLOR;
                hover = new Color(22, 163, 74);
                fg = Color.WHITE;
                break;
            case WARNING:
                bg = WARNING_COLOR;
                hover = new Color(217, 119, 6);
                fg = Color.WHITE;
                break;
            case DANGER:
                bg = DANGER_COLOR;
                hover = new Color(220, 38, 38);
                fg = Color.WHITE;
                break;
            case SECONDARY:
            default:
                bg = Color.WHITE;
                hover = new Color(243, 244, 246);
                fg = TEXT_SECONDARY;
                break;
        }

        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(DEFAULT_FONT);
        button.setBorder(createButtonBorder(bg, style != ButtonStyle.SECONDARY));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(BUTTON_SIZE);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    public static void styleInputField(JComponent field) {
        field.setBorder(createInputBorder());
        field.setFont(DEFAULT_FONT);
        field.setPreferredSize(INPUT_SIZE);

        if (field instanceof JTextField) {
            JTextField textField = (JTextField) field;
            textField.setBackground(Color.WHITE);
            textField.setForeground(TEXT_COLOR);

            if (!textField.isEnabled()) {
                textField.setBackground(BACKGROUND_COLOR);
                textField.setForeground(TEXT_SECONDARY);
            }
        } else if (field instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) field;
            comboBox.setBackground(Color.WHITE);
            comboBox.setForeground(TEXT_COLOR);
        }
    }

    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        return card;
    }

    public static JLabel createSectionHeader(String text) {
        JLabel header = new JLabel(text);
        header.setFont(BOLD_FONT);
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return header;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    public static void showStyledMessage(Component parent, String message, String title, MessageType type) {
        String icon;
        Color color;

        switch (type) {
            case SUCCESS:
                icon = "✅";
                color = SUCCESS_COLOR;
                break;
            case WARNING:
                icon = "⚠️";
                color = WARNING_COLOR;
                break;
            case ERROR:
                icon = "❌";
                color = DANGER_COLOR;
                break;
            case INFO:
            default:
                icon = "ℹ️";
                color = PRIMARY_COLOR;
                break;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(CARD_COLOR);

        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        content.setBackground(CARD_COLOR);

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(iconLabel, BorderLayout.WEST);

        // Message
        JLabel messageLabel = new JLabel("<html><div style='width:250px'>" + message + "</div></html>");
        messageLabel.setFont(DEFAULT_FONT);
        messageLabel.setForeground(TEXT_COLOR);
        content.add(messageLabel, BorderLayout.CENTER);

        // Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CARD_COLOR);
        JButton okButton = new JButton("OK");
        styleButton(okButton, ButtonStyle.PRIMARY);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        content.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(content);
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void applyGlobalLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, DEFAULT_FONT);
            }
        }
    }

    private static Border createButtonBorder(Color color, boolean isPrimary) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isPrimary ? color : BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private static Border createInputBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    public enum ButtonStyle {
        PRIMARY, SECONDARY, SUCCESS, WARNING, DANGER
    }

    public enum MessageType {
        INFO, SUCCESS, WARNING, ERROR
    }
}