package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
public class CreateOrEditUserModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> roleCombo = new JComboBox<>(new String[]{UserRole.Manager.name(), UserRole.Receptionist.name()});
    private final JComboBox<String> statusCombo = new JComboBox<>(new String[]{UserStatus.Active.name(), UserStatus.Inactive.name()});

    public CreateOrEditUserModal(JFrame parent) {
        super(parent, "Create or Edit User", true);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(saveBtn);
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);


                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
        
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);
        
                panel.add(loginBtn);
                cancelBtn.addActionListener(_ -> {
                    dispose();
                    System.exit(0);
                });
                panel.add(cancelBtn);
        
                add(panel);
        
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        dispose();
                        System.exit(0);
                    }
                });
    }

    public void onSave(ActionListener l) { saveBtn.addActionListener(l); }
    public void onCancel(ActionListener l) { cancelBtn.addActionListener(l); }

    public void setModel(User user) {
        // Set user data to the modal fields
    }
}