package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class ServiceView extends JPanel {
    private final JTable table = new JTable();

    private final JButton addBtn = new JButton("Add");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");

    private final JTextField searchField = new JTextField(20);
    private final JButton searchBtn = new JButton("Search");

    private int selectedRow = -1;

    public ServiceView() {
        super(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(1, 2));

        // Left panel: CRUD buttons
        JPanel topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeft.add(addBtn);
        topLeft.add(editBtn);
        topLeft.add(deleteBtn);
        top.add(topLeft);

        // Right panel: search
        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRight.add(searchField);
        topRight.add(searchBtn);
        top.add(topRight);

        add(top, BorderLayout.NORTH);

        // Table setup
        table.setShowGrid(true);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = table.getSelectedRow();
                    boolean rowSelected = selectedRow >= 0;
                    editBtn.setEnabled(rowSelected);
                    deleteBtn.setEnabled(rowSelected);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setTableModel(TableModel model) {
        table.setModel(model);
        table.clearSelection();
        selectedRow = -1;
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
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
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error - " + Constants.ErrorTitle.SERVICE,
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success - " + Constants.ErrorTitle.SERVICE,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
