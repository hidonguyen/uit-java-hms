package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IRoomTypeService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.roomType.CreateOrEditRoomTypeModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.roomType.RoomTypeView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class RoomTypePresenter {
    private final RoomTypeView view;
    private final CreateOrEditRoomTypeModal modal;
    private final IRoomTypeService service;

    private final NumberFormat vndFormat;

    private List<RoomType> roomTypes = new ArrayList<>();

    public RoomTypePresenter(JFrame parentFrame) {
        this.view = new RoomTypeView();
        this.modal = new CreateOrEditRoomTypeModal(parentFrame);
        this.service = DIContainer.getInstance().getRoomTypeService();

        this.vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        this.vndFormat.setMaximumFractionDigits(0);

        this.view.onAdd(_ -> {
            modal.setModel(null);
            modal.setVisible(true);
        });

        this.view.onEdit(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < roomTypes.size()) {
                RoomType rt = roomTypes.get(selectedRow);
                modal.setModel(rt);
                modal.setVisible(true);
            }
        });

        this.view.onDelete(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < roomTypes.size()) {
                RoomType rt = roomTypes.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to delete room type: " + rt.getCode() + " - " + rt.getName() + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new SwingWorker<Integer, Void>() {
                        @Override
                        protected Integer doInBackground() {
                            return service.delete(rt.getId());
                        }

                        @Override
                        protected void done() {
                            try {
                                if (get() > 0) {
                                    view.showSuccessMessage(Constants.SuccessMessage.DELETE_ROOM_TYPE_SUCCESS);
                                }
                            } catch (Exception ex) {
                                view.showErrorMessage(ex.getMessage());
                            }
                            loadData();
                        }
                    }.execute();
                }
            }
        });

        this.view.onSearch(_ -> {
            String q = view.getSearchQuery();
            if (q == null || q.isEmpty()) {
                view.setTableModel(new RoomTypeTableModel(roomTypes, vndFormat));
            } else {
                String qq = q.toLowerCase();
                List<RoomType> filtered = roomTypes.stream()
                        .filter(rt -> (rt.getCode() != null && rt.getCode().toLowerCase().contains(qq))
                                || (rt.getName() != null && rt.getName().toLowerCase().contains(qq)))
                        .toList();
                view.setTableModel(new RoomTypeTableModel(filtered, vndFormat));
            }
        });

        this.modal.onSave(_ -> {
            if (!modal.isValidInput())
                return;

            RoomType rt = modal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    try {
                        return rt.getId() == null ? service.create(rt) : service.update(rt);
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get() > 0) {
                            view.showSuccessMessage(rt.getId() == null
                                    ? Constants.SuccessMessage.CREATE_ROOM_TYPE_SUCCESS
                                    : Constants.SuccessMessage.UPDATE_ROOM_TYPE_SUCCESS);
                        }
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                    }
                    modal.setVisible(false);
                    loadData();
                }
            }.execute();
        });

        loadData();
    }

    public RoomTypeView getView() {
        view.authorize();
        return view;
    }

    public void loadData() {
        new SwingWorker<List<RoomType>, Void>() {
            @Override
            protected List<RoomType> doInBackground() {
                return service.findAll();
            }

            @Override
            protected void done() {
                try {
                    roomTypes = get();
                    view.setTableModel(new RoomTypeTableModel(roomTypes, vndFormat));
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }

    static class RoomTypeTableModel extends AbstractTableModel {
        private final String[] cols = {
                "Id", "Code", "Name",
                "Base Occ", "Max Occ",
                "Base Rate", "Hour Rate",
                "Extra Adult", "Extra Child",
                "Description",
                "Created At", "Created By",
                "Updated At", "Updated By"
        };
        private final List<RoomType> data;

        private final NumberFormat vndFormat;

        RoomTypeTableModel(List<RoomType> data, NumberFormat vndFormat) {
            this.data = data != null ? data : Collections.emptyList();
            this.vndFormat = vndFormat;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public String getColumnName(int c) {
            return cols[c];
        }

        @Override
        public Object getValueAt(int r, int c) {
            RoomType o = data.get(r);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getCode();
                case 2 -> o.getName();
                case 3 -> o.getBaseOccupancy();
                case 4 -> o.getMaxOccupancy();
                case 5 -> vndFormat.format(o.getBaseRate());
                case 6 -> vndFormat.format(o.getHourRate());
                case 7 -> vndFormat.format(o.getExtraAdultFee());
                case 8 -> vndFormat.format(o.getExtraChildFee());
                case 9 -> o.getDescription();
                case 10 -> o.getCreatedAt() == null ? null : o.getCreatedAt().format(fmt);
                case 11 -> o.getCreatedBy();
                case 12 -> o.getUpdatedAt() == null ? null : o.getUpdatedAt().format(fmt);
                case 13 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}
