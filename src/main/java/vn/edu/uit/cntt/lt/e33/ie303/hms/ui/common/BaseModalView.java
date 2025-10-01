package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public abstract class BaseModalView extends JDialog {
    protected final JButton saveBtn = new JButton("Save");
    protected final JButton cancelBtn = new JButton("Cancel");
    protected JPanel formPanel;
    protected GridBagConstraints gc;

    protected static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    protected static final Color PRIMARY_HOVER = new Color(37, 99, 235);
    protected static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    protected static final Color DANGER_COLOR = new Color(239, 68, 68);
    protected static final Color BACKGROUND_COLOR = new Color(249, 250, 251);
    protected static final Color CARD_COLOR = Color.WHITE;
    protected static final Color TEXT_COLOR = new Color(17, 24, 39);
    protected static final Color TEXT_SECONDARY = new Color(75, 85, 99);
    protected static final Color BORDER_COLOR = new Color(229, 231, 235);

    public BaseModalView(JFrame parent, String title) {
        super(parent, title, true);
        initializeModal();
        setupLayout();
        applyModernStyling();
        setupEventHandlers();
    }

    private void initializeModal() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);

        formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footer = createFooter();
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD_COLOR);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        JLabel titleLabel = new JLabel(getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        header.add(titleLabel, BorderLayout.WEST);

        return header;
    }

    protected JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        footer.setBackground(BACKGROUND_COLOR);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        footer.add(cancelBtn);
        footer.add(saveBtn);

        return footer;
    }

    private void applyModernStyling() {
        styleButton(saveBtn, PRIMARY_COLOR, PRIMARY_HOVER, Color.WHITE, true);

        styleButton(cancelBtn, Color.WHITE, new Color(243, 244, 246), TEXT_SECONDARY, false);

        setUIFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    protected void styleButton(JButton button, Color bg, Color hoverBg, Color fg, boolean isPrimary) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.CENTER_BASELINE, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isPrimary ? bg : BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    protected void addFormField(String label, JComponent field, int row, int col) {
        addFormField(label, field, row, col, 1);
    }

    protected void addFormField(String label, JComponent field, int row, int col, int colspan) {
        int colBase = col * 2;

        gc.gridx = colBase;
        gc.gridy = row;
        gc.gridwidth = 1;
        gc.weightx = 0;

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.CENTER_BASELINE, 13));
        labelComponent.setForeground(TEXT_COLOR);
        formPanel.add(labelComponent, gc);

        gc.gridx = colBase + 1;
        gc.gridwidth = colspan;
        gc.weightx = 1;

        styleFormField(field);
        formPanel.add(field, gc);
    }

    protected void styleFormField(JComponent field) {
        if (field instanceof JTextField) {
            JTextField textField = (JTextField) field;
            textField.setBorder(createFieldBorder());
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textField.setBackground(Color.WHITE);
            textField.setForeground(TEXT_COLOR);

            if (!textField.isEnabled()) {
                textField.setBackground(new Color(249, 250, 251));
                textField.setForeground(TEXT_SECONDARY);
            }
        } else if (field instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) field;
            comboBox.setBorder(createFieldBorder());
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            comboBox.setBackground(Color.WHITE);
            comboBox.setForeground(TEXT_COLOR);
        }

        field.setPreferredSize(new Dimension(180, 32));
    }

    private Border createFieldBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10));
    }

    private void setupEventHandlers() {
        cancelBtn.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        getRootPane().registerKeyboardAction(
                e -> saveBtn.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    protected void setUIFont(Font font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    public void showStyledError(String message, String title) {
        JDialog errorDialog = new JDialog(this, title, true);
        errorDialog.setLayout(new BorderLayout());
        errorDialog.getContentPane().setBackground(Color.WHITE);

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.setBackground(Color.WHITE);

        JLabel messageLabel = new JLabel(
                "<html><div style='max-width:300px;'>" + (message == null ? "" : message) + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(TEXT_COLOR);
        content.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        JButton okButton = new JButton("OK");
        styleButton(okButton, PRIMARY_COLOR, PRIMARY_HOVER, Color.WHITE, true);
        okButton.addActionListener(e -> errorDialog.dispose());
        buttonPanel.add(okButton);
        content.add(buttonPanel, BorderLayout.SOUTH);

        errorDialog.add(content);
        errorDialog.pack();
        errorDialog.setMinimumSize(new Dimension(320, errorDialog.getHeight()));
        errorDialog.setLocationRelativeTo(this);
        errorDialog.setVisible(true);
    }

    public void onSave(ActionListener listener) {
        saveBtn.addActionListener(listener);
    }

    public abstract boolean isValidInput();

    public abstract Object getModel();

    public abstract void setModel(Object model);

    public void clearForm() {
        
    }

    protected void finalizeModal() {
        pack();
        setLocationRelativeTo(getParent());
    }
}
