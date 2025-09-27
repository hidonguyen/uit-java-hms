package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IRoomService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.room.CreateOrEditRoomModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.room.RoomView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class RoomPresenter {
    private final RoomView view;
    private final CreateOrEditRoomModal modal;
    private final IRoomService service;

    private List<Room> rooms = new ArrayList<>();

    public RoomPresenter(JFrame parentFrame) {
        this.view = new RoomView();
        this.modal = new CreateOrEditRoomModal(parentFrame);
        this.service = DIContainer.getInstance().getRoomService();

        this.view.onAdd(_ -> {
            modal.setModel(null);
            modal.setVisible(true);
        });

        this.view.onEdit(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < rooms.size()) {
                Room r = rooms.get(selectedRow);
                modal.setModel(r);
                modal.setVisible(true);
            }
        });

        this.view.onDelete(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < rooms.size()) {
                Room r = rooms.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to delete room: " + r.getName() + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new SwingWorker<Integer, Void>() {
                        @Override
                        protected Integer doInBackground() {
                            return service.delete(r.getId());
                        }

                        @Override
                        protected void done() {
                            try {
                                if (get() > 0) {
                                    view.showSuccessMessage(Constants.SuccessMessage.DELETE_ROOM_SUCCESS);
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
                view.setTableModel(new RoomTableModel(rooms));
            } else {
                String qq = q.toLowerCase();
                List<Room> filtered = rooms.stream()
                        .filter(r -> (r.getName() != null && r.getName().toLowerCase().contains(qq))
                                || String.valueOf(r.getRoomTypeId()).contains(qq))
                        .toList();
                view.setTableModel(new RoomTableModel(filtered));
            }
        });

        this.modal.onSave(_ -> {
            if (!modal.isValidInput())
                return;

            Room r = modal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    try {
                        return r.getId() == null ? service.create(r) : service.update(r);
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get() > 0) {
                            view.showSuccessMessage(r.getId() == null
                                    ? Constants.SuccessMessage.CREATE_ROOM_SUCCESS
                                    : Constants.SuccessMessage.UPDATE_ROOM_SUCCESS);
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

    public RoomView getView() {
        return view;
    }

    public void loadData() {
        new SwingWorker<List<Room>, Void>() {
            @Override
            protected List<Room> doInBackground() {
                return service.findAll();
            }

            @Override
            protected void done() {
                try {
                    rooms = get();
                    view.setTableModel(new RoomTableModel(rooms));
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }

    static class RoomTableModel extends AbstractTableModel {
        private final String[] cols = {
                "Id", "Name", "Room Type Id", "Housekeeping", "Status",
                "Created At", "Created By", "Updated At", "Updated By"
        };
        private final List<Room> data;

        RoomTableModel(List<Room> data) {
            this.data = data;
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
            Room o = data.get(r);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getName();
                case 2 -> o.getRoomTypeId();
                case 3 -> o.getHousekeepingStatus();
                case 4 -> o.getStatus();
                case 5 -> o.getCreatedAt() == null ? null : o.getCreatedAt().format(fmt);
                case 6 -> o.getCreatedBy();
                case 7 -> o.getUpdatedAt() == null ? null : o.getUpdatedAt().format(fmt);
                case 8 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}
