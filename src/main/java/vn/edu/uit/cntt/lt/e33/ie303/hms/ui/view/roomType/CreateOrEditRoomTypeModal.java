package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.roomType;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditRoomTypeModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField = new JTextField(20);
    private final JTextField codeField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JTextField baseOccupancyField = new JTextField(20);
    private final JTextField maxOccupancyField = new JTextField(20);
    private final JTextField baseRateField = new JTextField(20);
    private final JTextField hourRateField = new JTextField(20);
    private final JTextField extraAdultFeeField = new JTextField(20);
    private final JTextField extraChildFeeField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);

    private final JTextField createdAtField = new JTextField(20);
    private final JTextField createdByField = new JTextField(20);
    private final JTextField updatedAtField = new JTextField(20);
    private final JTextField updatedByField = new JTextField(20);

    public CreateOrEditRoomTypeModal(JFrame parent) {
        super(parent, "Create or Edit Room Type", true);
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(saveBtn);
        cancelBtn.addActionListener(_ -> dispose());
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(14, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        idField.setEnabled(false);
        panel.add(idField);

        panel.add(new JLabel("Code:"));
        panel.add(codeField);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Base Occupancy:"));
        panel.add(baseOccupancyField);

        panel.add(new JLabel("Max Occupancy:"));
        panel.add(maxOccupancyField);

        panel.add(new JLabel("Base Rate:"));
        panel.add(baseRateField);

        panel.add(new JLabel("Hour Rate:"));
        panel.add(hourRateField);

        panel.add(new JLabel("Extra Adult Fee:"));
        panel.add(extraAdultFeeField);

        panel.add(new JLabel("Extra Child Fee:"));
        panel.add(extraChildFeeField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Created At:"));
        createdAtField.setEnabled(false);
        panel.add(createdAtField);

        panel.add(new JLabel("Created By:"));
        createdByField.setEnabled(false);
        panel.add(createdByField);

        panel.add(new JLabel("Updated At:"));
        updatedAtField.setEnabled(false);
        panel.add(updatedAtField);

        panel.add(new JLabel("Updated By:"));
        updatedByField.setEnabled(false);
        panel.add(updatedByField);

        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void onSave(ActionListener l) {
        saveBtn.addActionListener(l);
    }

    public boolean isValidInput() {
        if (codeField.getText().trim().isEmpty()) {
            showValidationError("Code cannot be empty");
            return false;
        }
        if (nameField.getText().trim().isEmpty()) {
            showValidationError("Name cannot be empty");
            return false;
        }
        return true;
    }

    public RoomType getModel() {
        RoomType rt = new RoomType();
        if (!idField.getText().trim().isEmpty()) {
            rt.setId(Long.parseLong(idField.getText().trim()));
        }
        rt.setCode(codeField.getText().trim());
        rt.setName(nameField.getText().trim());
        rt.setBaseOccupancy(Integer.parseInt(baseOccupancyField.getText().trim()));
        rt.setMaxOccupancy(Integer.parseInt(maxOccupancyField.getText().trim()));
        rt.setBaseRate(Double.parseDouble(baseRateField.getText().trim()));
        rt.setHourRate(Double.parseDouble(hourRateField.getText().trim()));
        rt.setExtraAdultFee(Double.parseDouble(extraAdultFeeField.getText().trim()));
        rt.setExtraChildFee(Double.parseDouble(extraChildFeeField.getText().trim()));
        rt.setDescription(descriptionField.getText().trim());
        return rt;
    }

    public void setModel(RoomType rt) {
        if (rt == null) {
            idField.setText("");
            codeField.setText("");
            nameField.setText("");
            baseOccupancyField.setText("");
            maxOccupancyField.setText("");
            baseRateField.setText("");
            hourRateField.setText("");
            extraAdultFeeField.setText("");
            extraChildFeeField.setText("");
            descriptionField.setText("");
        } else {
            idField.setText(rt.getId() != null ? rt.getId().toString() : "");
            codeField.setText(rt.getCode());
            nameField.setText(rt.getName());
            baseOccupancyField.setText(String.valueOf(rt.getBaseOccupancy()));
            maxOccupancyField.setText(String.valueOf(rt.getMaxOccupancy()));
            baseRateField.setText(String.valueOf(rt.getBaseRate()));
            hourRateField.setText(String.valueOf(rt.getHourRate()));
            extraAdultFeeField.setText(String.valueOf(rt.getExtraAdultFee()));
            extraChildFeeField.setText(String.valueOf(rt.getExtraChildFee()));
            descriptionField.setText(rt.getDescription());
        }
    }

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error - " + Constants.ErrorTitle.ROOM_TYPE,
                JOptionPane.ERROR_MESSAGE);
    }
}
