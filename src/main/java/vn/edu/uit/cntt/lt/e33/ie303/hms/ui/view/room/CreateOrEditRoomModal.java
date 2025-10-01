package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.room;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.HousekeepingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.RoomStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditRoomModal extends BaseModalView {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField descriptionField = new JTextField();
    private final JComboBox<RoomType> roomTypeSelect = new JComboBox<>();
    private final JComboBox<HousekeepingStatus> housekeepingCombo = new JComboBox<>(HousekeepingStatus.values());
    private final JComboBox<RoomStatus> statusCombo = new JComboBox<>(RoomStatus.values());
    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    public CreateOrEditRoomModal(JFrame parent) {
        super(parent, "Create or Edit Room");
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        idField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        roomTypeSelect.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof RoomType rt) {
                    String code = rt.getCode() != null ? rt.getCode() : "";
                    String name = rt.getName() != null ? rt.getName() : "";
                    String text = (!code.isEmpty() && !name.isEmpty()) ? (code + " – " + name)
                            : (!code.isEmpty() ? code : (!name.isEmpty() ? name : "— Select —"));
                    setText(text);
                }
                return this;
            }
        });

        int row = 0;
        addFormField("ID:", idField, row, 0);
        addFormField("Name:", nameField, row++, 1);

        addFormField("Room Type:", roomTypeSelect, row, 0);
        addFormField("Description:", descriptionField, row++, 1);

        addFormField("Housekeeping:", housekeepingCombo, row, 0);
        addFormField("Status:", statusCombo, row++, 1);

        addFormField("Created At:", createdAtField, row, 0);
        addFormField("Created By:", createdByField, row++, 1);

        addFormField("Updated At:", updatedAtField, row, 0);
        addFormField("Updated By:", updatedByField, row, 1);
    }

    public void loadRoomTypes(List<RoomType> list) {
        DefaultComboBoxModel<RoomType> model = new DefaultComboBoxModel<>();
        if (list != null) {
            for (RoomType rt : list)
                model.addElement(rt);
        }
        roomTypeSelect.setModel(model);
        if (model.getSize() > 0)
            roomTypeSelect.setSelectedIndex(0);
    }

    public RoomType getSelectedRoomType() {
        Object sel = roomTypeSelect.getSelectedItem();
        return (sel instanceof RoomType) ? (RoomType) sel : null;
    }

    @Override
    public boolean isValidInput() {
        if (nameField.getText().trim().isEmpty()) {
            showStyledError(Constants.ValidateMessage.ROOM_NAME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.ROOM);
            return false;
        }
        RoomType sel = getSelectedRoomType();
        if (sel == null || sel.getId() == null) {
            showStyledError(Constants.ValidateMessage.ROOM_TYPE_MUST_BE_SELECTED,
                    "Validation Error - " + Constants.ErrorTitle.ROOM);
            return false;
        }
        return true;
    }

    @Override
    public Room getModel() {
        Room r = new Room();
        if (!idField.getText().trim().isEmpty())
            r.setId(Long.parseLong(idField.getText().trim()));
        r.setName(nameField.getText().trim());

        RoomType sel = getSelectedRoomType();
        if (sel != null && sel.getId() != null)
            r.setRoomTypeId(sel.getId());

        r.setDescription(descriptionField.getText().trim());
        r.setHousekeepingStatus((HousekeepingStatus) housekeepingCombo.getSelectedItem());
        r.setStatus((RoomStatus) statusCombo.getSelectedItem());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        if (!createdAtField.getText().trim().isEmpty())
            r.setCreatedAt(
                    OffsetDateTime.of(LocalDateTime.parse(createdAtField.getText().trim(), fmt), ZoneOffset.UTC));
        if (!createdByField.getText().trim().isEmpty())
            r.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        if (!updatedAtField.getText().trim().isEmpty())
            r.setUpdatedAt(
                    OffsetDateTime.of(LocalDateTime.parse(updatedAtField.getText().trim(), fmt), ZoneOffset.UTC));
        if (!updatedByField.getText().trim().isEmpty())
            r.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));
        return r;
    }

    @Override
    public void setModel(Object model) {
        Room r = (Room) model;
        if (r == null) {
            idField.setText("");
            nameField.setText("");
            descriptionField.setText("");
            if (roomTypeSelect.getItemCount() > 0)
                roomTypeSelect.setSelectedIndex(0);
            housekeepingCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
            createdAtField.setText("");
            createdByField.setText("");
            updatedAtField.setText("");
            updatedByField.setText("");
            return;
        }

        idField.setText(r.getId() != null ? r.getId().toString() : "");
        nameField.setText(r.getName() != null ? r.getName() : "");
        descriptionField.setText(r.getDescription() != null ? r.getDescription() : "");
        selectRoomTypeById(r.getRoomTypeId());
        if (r.getHousekeepingStatus() != null)
            housekeepingCombo.setSelectedItem(r.getHousekeepingStatus());
        else
            housekeepingCombo.setSelectedIndex(0);
        if (r.getStatus() != null)
            statusCombo.setSelectedItem(r.getStatus());
        else
            statusCombo.setSelectedIndex(0);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        createdAtField.setText(r.getCreatedAt() != null ? r.getCreatedAt().format(fmt) : "");
        createdByField.setText(r.getCreatedBy() != null ? r.getCreatedBy().toString() : "");
        updatedAtField.setText(r.getUpdatedAt() != null ? r.getUpdatedAt().format(fmt) : "");
        updatedByField.setText(r.getUpdatedBy() != null ? r.getUpdatedBy().toString() : "");
    }

    private void selectRoomTypeById(Long roomTypeId) {
        if (roomTypeId == null) {
            if (roomTypeSelect.getItemCount() > 0)
                roomTypeSelect.setSelectedIndex(0);
            return;
        }
        ComboBoxModel<RoomType> model = roomTypeSelect.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            RoomType item = model.getElementAt(i);
            if (item != null && roomTypeId.equals(item.getId())) {
                roomTypeSelect.setSelectedIndex(i);
                return;
            }
        }
        if (roomTypeSelect.getItemCount() > 0)
            roomTypeSelect.setSelectedIndex(0);
    }
}
