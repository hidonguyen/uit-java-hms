package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private final JTable table = new JTable();
    private final JButton refreshBtn = new JButton("Refresh");

    public UserView() {
        super("Swing App – Users");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(refreshBtn);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    public void setTableModel(TableModel model) { table.setModel(model); }
    public void onRefresh(ActionListener l) { refreshBtn.addActionListener(l); }
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void showWindow() { setVisible(true); }
}