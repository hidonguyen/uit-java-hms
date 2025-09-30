package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public abstract class BaseTableView extends JPanel {
    protected static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    protected static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    protected static final Color ACCENT_COLOR = new Color(16, 185, 129);
    protected static final Color DANGER_COLOR = new Color(239, 68, 68);
    protected static final Color TEXT_COLOR = new Color(51, 65, 85);
    protected static final Color BORDER_COLOR = new Color(226, 232, 240);

    protected final JTable table = new JTable();
    protected final JButton addBtn;
    protected final JButton editBtn;
    protected final JButton deleteBtn;
    protected final JTextField searchField;
    protected final JButton searchBtn;

    protected JPanel actionPanel;

    private int selectedRow = -1;

    protected abstract String getModuleName();

    protected abstract String getAddButtonText();

    protected abstract String getSearchPlaceholder();

    protected abstract String getErrorTitle();

    protected void onInitExtraActions(JPanel actionPanel) {
    }

    protected void onRowSelectionChanged(boolean rowSelected) {
    }

    public BaseTableView() {
        super(new BorderLayout());

        this.addBtn = createModernButton(getAddButtonText(), PRIMARY_COLOR, Color.WHITE);
        this.editBtn = createModernButton("Edit", ACCENT_COLOR, Color.WHITE);
        this.deleteBtn = createModernButton("Delete", DANGER_COLOR, Color.WHITE);
        this.searchField = createModernTextField(getSearchPlaceholder());
        this.searchBtn = createModernButton("Search", PRIMARY_COLOR, Color.WHITE);

        initializeComponents();
    }

    private void initializeComponents() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        setupButtons();
        setupLayout();
        setupTable();
    }

    private void setupButtons() {
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

        addHoverEffect(addBtn, PRIMARY_COLOR);
        addHoverEffect(editBtn, ACCENT_COLOR);
        addHoverEffect(deleteBtn, DANGER_COLOR);
        addHoverEffect(searchBtn, PRIMARY_COLOR);
    }

    private void setupLayout() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);

        onInitExtraActions(actionPanel);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(getModuleName() + " Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        searchPanel.setBackground(Color.WHITE);

        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setBackground(SECONDARY_COLOR);
        searchContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 5)));
        searchContainer.setPreferredSize(new Dimension(280, 40));

        searchField.setBorder(null);
        searchField.setBackground(SECONDARY_COLOR);
        searchContainer.add(searchField, BorderLayout.CENTER);

        searchBtn.setPreferredSize(new Dimension(80, 30));
        searchBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchContainer.add(searchBtn, BorderLayout.EAST);

        searchPanel.add(searchContainer);
        return searchPanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        actionPanel.setBackground(Color.WHITE);

        addBtn.setPreferredSize(new Dimension(140, 40));
        editBtn.setPreferredSize(new Dimension(100, 40));
        deleteBtn.setPreferredSize(new Dimension(100, 40));

        actionPanel.add(addBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(editBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(deleteBtn);

        return actionPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 0, 0, 0)));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void setupTable() {
        table.setShowGrid(false);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_COLOR);
        table.setGridColor(BORDER_COLOR);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground((row % 2 == 0) ? Color.WHITE : new Color(249, 250, 251));
                }
                setBorder(new EmptyBorder(10, 15, 10, 15));
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = table.getSelectedRow();
                    boolean rowSelected = selectedRow >= 0;
                    editBtn.setEnabled(rowSelected);
                    deleteBtn.setEnabled(rowSelected);

                    updateButtonState(editBtn, rowSelected);
                    updateButtonState(deleteBtn, rowSelected);

                    onRowSelectionChanged(rowSelected);
                }
            }
        });
    }

    protected JButton createModernButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        return field;
    }

    private void addHoverEffect(JButton button, Color originalColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled())
                    button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled())
                    button.setBackground(originalColor);
            }
        });
    }

    private void updateButtonState(JButton button, boolean enabled) {
        if (enabled) {
            button.setOpaque(true);
        } else {
            button.setOpaque(false);
        }
        button.setEnabled(enabled);
        button.repaint();
    }

    public void setTableModel(TableModel model) {
        table.setModel(model);
        table.clearSelection();
        selectedRow = -1;
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        updateButtonState(editBtn, false);
        updateButtonState(deleteBtn, false);
        onRowSelectionChanged(false);
    }

    public void onAdd(ActionListener l) {
        addBtn.addActionListener(l);
    }

    public void onEdit(ActionListener l) {
        editBtn.addActionListener(l);
    }

    public void onDelete(ActionListener l) {
        deleteBtn.addActionListener(l);
    }

    public void onSearch(ActionListener l) {
        searchBtn.addActionListener(l);
        searchField.addActionListener(l);
    }

    public String getSearchQuery() {
        String query = searchField.getText().trim();
        return query.equals(getSearchPlaceholder()) ? "" : query;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public JTable getTable() {
        return table;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='padding: 10px; font-family: Segoe UI; font-size: 14px;'>" + message
                        + "</div></html>",
                "Error - " + getErrorTitle(),
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><div style='padding: 10px; font-family: Segoe UI; font-size: 14px;'>" + message
                        + "</div></html>",
                "Success - " + getErrorTitle(),
                JOptionPane.INFORMATION_MESSAGE);
    }
}
