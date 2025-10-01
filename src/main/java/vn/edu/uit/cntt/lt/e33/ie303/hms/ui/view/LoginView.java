package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginBtn = new JButton("Login");
    private final JButton cancelBtn = new JButton("Cancel");
    private final JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
    private final JToggleButton showPasswordBtn = new JToggleButton();
    private boolean isPasswordVisible = false;

    // Color scheme - Modern flat design
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color PRIMARY_DARK = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color TEXT_COLOR = new Color(52, 73, 94);
    private static final Color BORDER_COLOR = new Color(220, 224, 228);

    private static Font pickEmojiFont(int size) {
        String[] candidates = {
                "Segoe UI Emoji", // Windows
                "Apple Color Emoji", // macOS
                "Noto Color Emoji" // Linux
        };
        for (String name : candidates) {
            Font f = new Font(name, Font.PLAIN, size);
            if (f.getFamily().equals(name))
                return f;
        }
        return new Font("Segoe UI Symbol", Font.PLAIN, size); // fallback mono
    }

    private static String cp(int codepoint) {
        return new String(Character.toChars(codepoint));
    }

    private final String EYE = cp(0x1F441);
    private final String MONKEY_SEE_NO = cp(0x1F648);

    public LoginView() {
        setTitle("UIT Hotel Management Pro");
        setSize(480, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = createHeaderPanel();
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Icon
        JLabel iconLabel = new JLabel("🏨");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));

        JLabel usernameLabel = createFieldLabel("Username");
        JPanel usernamePanel = createInputPanel(usernameField, null);

        JLabel passwordLabel = createFieldLabel("Password");
        JPanel passwordPanel = createInputPanel(passwordField, showPasswordBtn);

        configureShowPasswordButton();

        rememberMeCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rememberMeCheckBox.setForeground(TEXT_COLOR);
        rememberMeCheckBox.setBackground(Color.WHITE);
        rememberMeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        rememberMeCheckBox.setFocusPainted(false);

        formPanel.add(usernameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(usernamePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(passwordLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        formPanel.add(rememberMeCheckBox);

        return formPanel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createInputPanel(JTextField field, JToggleButton toggleBtn) {
        JPanel outerPanel = new JPanel(new BorderLayout(0, 0));
        outerPanel.setBackground(Color.WHITE);
        outerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        outerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel wrapperPanel = new JPanel(new BorderLayout(0, 0));
        wrapperPanel.setBackground(Color.WHITE);

        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        wrapperPanel.setBorder(lineBorder);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);
        field.setCaretColor(PRIMARY_COLOR);

        Border padding = BorderFactory.createEmptyBorder(12, 16, 12, 16);
        field.setBorder(padding);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                wrapperPanel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                wrapperPanel.setBorder(lineBorder);
            }
        });

        wrapperPanel.add(field, BorderLayout.CENTER);

        if (toggleBtn != null) {
            JPanel buttonWrapper = new JPanel(new BorderLayout());
            buttonWrapper.setBackground(Color.WHITE);
            buttonWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
            buttonWrapper.add(toggleBtn, BorderLayout.CENTER);
            wrapperPanel.add(buttonWrapper, BorderLayout.EAST);
        }

        outerPanel.add(wrapperPanel, BorderLayout.CENTER);

        return outerPanel;
    }

    private void configureShowPasswordButton() {
        showPasswordBtn.setPreferredSize(new Dimension(48, 48));
        showPasswordBtn.setFont(pickEmojiFont(18)); // << đổi font có emoji
        showPasswordBtn.setText(EYE); // << dùng codepoint
        showPasswordBtn.setFocusPainted(false);
        showPasswordBtn.setBorderPainted(false);
        showPasswordBtn.setContentAreaFilled(false);
        showPasswordBtn.setBackground(Color.WHITE);
        showPasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPasswordBtn.setToolTipText("Show password");

        showPasswordBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showPasswordBtn.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showPasswordBtn.setForeground(TEXT_COLOR);
            }
        });

        showPasswordBtn.addActionListener(e -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                passwordField.setEchoChar((char) 0);
                showPasswordBtn.setText(MONKEY_SEE_NO);
                showPasswordBtn.setToolTipText("Hide password");
            } else {
                passwordField.setEchoChar('●');
                showPasswordBtn.setText(EYE);
                showPasswordBtn.setToolTipText("Show password");
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // Login button
        styleModernButton(loginBtn, SUCCESS_COLOR, Color.WHITE);

        // Cancel button
        styleModernButton(cancelBtn, DANGER_COLOR, Color.WHITE);
        cancelBtn.addActionListener(_ -> {
            dispose();
            System.exit(0);
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        return buttonPanel;
    }

    private void styleModernButton(JButton button, Color bgColor, Color fgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(0, 50));

        button.addMouseListener(new MouseAdapter() {
            Color originalColor = bgColor;

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighten(originalColor, 0.9f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(darken(originalColor, 0.9f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(brighten(originalColor, 0.9f));
            }
        });
    }

    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() * (1 + (1 - factor))));
        int g = Math.min(255, (int) (color.getGreen() * (1 + (1 - factor))));
        int b = Math.min(255, (int) (color.getBlue() * (1 + (1 - factor))));
        return new Color(r, g, b);
    }

    private Color darken(Color color, float factor) {
        int r = (int) (color.getRed() * factor);
        int g = (int) (color.getGreen() * factor);
        int b = (int) (color.getBlue() * factor);
        return new Color(r, g, b);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isRememberMe() {
        return rememberMeCheckBox.isSelected();
    }

    public void onSubmit(ActionListener l) {
        loginBtn.addActionListener(l);

        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginBtn.doClick();
                }
            }
        };
        usernameField.addKeyListener(enterListener);
        passwordField.addKeyListener(enterListener);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        rememberMeCheckBox.setSelected(false);
        isPasswordVisible = false;
        passwordField.setEchoChar('●');
        showPasswordBtn.setText("👁");
        showPasswordBtn.setSelected(false);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}