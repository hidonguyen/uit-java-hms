package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ModernUI {

    // Modern Color Palette
    public static final Color PRIMARY_BLUE = new Color(0, 123, 255);
    public static final Color SUCCESS_GREEN = new Color(40, 167, 69);
    public static final Color DANGER_RED = new Color(220, 53, 69);
    public static final Color WARNING_ORANGE = new Color(255, 193, 7);
    public static final Color SECONDARY_GRAY = new Color(108, 117, 125);
    public static final Color LIGHT_GRAY = new Color(248, 249, 250);
    public static final Color DARK_GRAY = new Color(52, 58, 64);

    // Background colors
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color FORM_BACKGROUND = new Color(248, 249, 250);

    // Fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Style primary button (Save, Submit, etc.)
     */
    public static void stylePrimaryButton(JButton button) {
        button.setBackground(PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_BLUE.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_BLUE);
            }
        });
    }

    /**
     * Style secondary button (Cancel, Close, etc.)
     */
    public static void styleSecondaryButton(JButton button) {
        button.setBackground(SECONDARY_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_GRAY.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_GRAY);
            }
        });
    }

    /**
     * Style danger button (Delete, etc.)
     */
    public static void styleDangerButton(JButton button) {
        button.setBackground(DANGER_RED);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_RED.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_RED);
            }
        });
    }

    /**
     * Style text field with modern look
     */
    public static void styleTextField(JTextField field) {
        field.setFont(INPUT_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        field.setBackground(Color.WHITE);

        // Focus border
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_BLUE, 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });
    }

    /**
     * Style combo box
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(INPUT_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    /**
     * Style label with modern typography
     */
    public static void styleLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(DARK_GRAY);
    }

    /**
     * Create modern card panel
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    /**
     * Create modern form panel
     */
    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(FORM_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        return panel;
    }

    /**
     * Create section separator
     */
    public static JSeparator createSectionSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(222, 226, 230));
        return separator;
    }

    /**
     * Create modern dialog
     */
    public static void styleDialog(JDialog dialog) {
        dialog.setBackground(FORM_BACKGROUND);
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
}