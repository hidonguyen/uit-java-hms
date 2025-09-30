package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.roomType;

import javax.swing.JFrame;
import javax.swing.JTextField;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditRoomTypeModal extends BaseModalView {
    private final JTextField idField = new JTextField();
    private final JTextField codeField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField baseOccupancyField = new JTextField();
    private final JTextField maxOccupancyField = new JTextField();
    private final JTextField baseRateField = new JTextField();
    private final JTextField hourRateField = new JTextField();
    private final JTextField extraAdultFeeField = new JTextField();
    private final JTextField extraChildFeeField = new JTextField();
    private final JTextField descriptionField = new JTextField();

    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    public CreateOrEditRoomTypeModal(JFrame parent) {
        super(parent, "Create or Edit Room Type");
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        idField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        int row = 0;
        addFormField("ID:", idField, row, 0);
        addFormField("Code:", codeField, row++, 1);

        addFormField("Name:", nameField, row, 0);
        addFormField("Base Occupancy:", baseOccupancyField, row++, 1);

        addFormField("Max Occupancy:", maxOccupancyField, row, 0);
        addFormField("Base Rate:", baseRateField, row++, 1);

        addFormField("Hour Rate:", hourRateField, row, 0);
        addFormField("Extra Adult Fee:", extraAdultFeeField, row++, 1);

        addFormField("Extra Child Fee:", extraChildFeeField, row, 0);
        addFormField("Description:", descriptionField, row++, 1);

        addFormField("Created At:", createdAtField, row, 0);
        addFormField("Created By:", createdByField, row++, 1);

        addFormField("Updated At:", updatedAtField, row, 0);
        addFormField("Updated By:", updatedByField, row, 1);
    }

    @Override
    public boolean isValidInput() {
        if (codeField.getText().trim().isEmpty()) {
            showStyledError(Constants.ValidateMessage.ROOM_TYPE_CODE_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.ROOM_TYPE);
            return false;
        }
        if (nameField.getText().trim().isEmpty()) {
            showStyledError(Constants.ValidateMessage.ROOM_TYPE_NAME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.ROOM_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public RoomType getModel() {
        RoomType rt = new RoomType();
        if (!idField.getText().trim().isEmpty())
            rt.setId(Long.parseLong(idField.getText().trim()));
        rt.setCode(codeField.getText().trim());
        rt.setName(nameField.getText().trim());
        if (!baseOccupancyField.getText().trim().isEmpty())
            rt.setBaseOccupancy(Integer.parseInt(baseOccupancyField.getText().trim()));
        if (!maxOccupancyField.getText().trim().isEmpty())
            rt.setMaxOccupancy(Integer.parseInt(maxOccupancyField.getText().trim()));
        if (!baseRateField.getText().trim().isEmpty())
            rt.setBaseRate(Double.parseDouble(baseRateField.getText().trim()));
        if (!hourRateField.getText().trim().isEmpty())
            rt.setHourRate(Double.parseDouble(hourRateField.getText().trim()));
        if (!extraAdultFeeField.getText().trim().isEmpty())
            rt.setExtraAdultFee(Double.parseDouble(extraAdultFeeField.getText().trim()));
        if (!extraChildFeeField.getText().trim().isEmpty())
            rt.setExtraChildFee(Double.parseDouble(extraChildFeeField.getText().trim()));
        rt.setDescription(descriptionField.getText().trim());
        return rt;
    }

    @Override
    public void setModel(Object model) {
        RoomType rt = (RoomType) model;
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
            createdAtField.setText("");
            createdByField.setText("");
            updatedAtField.setText("");
            updatedByField.setText("");
            return;
        }
        idField.setText(rt.getId() != null ? rt.getId().toString() : "");
        codeField.setText(rt.getCode() != null ? rt.getCode() : "");
        nameField.setText(rt.getName() != null ? rt.getName() : "");
        baseOccupancyField.setText(String.valueOf(rt.getBaseOccupancy()));
        maxOccupancyField.setText(String.valueOf(rt.getMaxOccupancy()));
        baseRateField.setText(String.valueOf(rt.getBaseRate()));
        hourRateField.setText(String.valueOf(rt.getHourRate()));
        extraAdultFeeField.setText(String.valueOf(rt.getExtraAdultFee()));
        extraChildFeeField.setText(String.valueOf(rt.getExtraChildFee()));
        descriptionField.setText(rt.getDescription() != null ? rt.getDescription() : "");
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter
                .ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        createdAtField.setText(rt.getCreatedAt() != null ? rt.getCreatedAt().format(fmt) : "");
        createdByField.setText(rt.getCreatedBy() != null ? rt.getCreatedBy().toString() : "");
        updatedAtField.setText(rt.getUpdatedAt() != null ? rt.getUpdatedAt().format(fmt) : "");
        updatedByField.setText(rt.getUpdatedBy() != null ? rt.getUpdatedBy().toString() : "");
    }
}
