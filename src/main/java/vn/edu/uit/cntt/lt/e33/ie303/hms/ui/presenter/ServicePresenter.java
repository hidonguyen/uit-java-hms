package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IServiceService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service.CreateOrEditServiceModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service.ServiceView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class ServicePresenter {
    private final ServiceView view;
    private final CreateOrEditServiceModal modal;
    private final IServiceService service;

    private List<Service> services = new ArrayList<>();
    private final NumberFormat vndFormat;

    public ServicePresenter(JFrame parentFrame) {
        this.view = new ServiceView();
        this.modal = new CreateOrEditServiceModal(parentFrame);
        this.service = DIContainer.getInstance().getServiceService();
        this.vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        this.vndFormat.setMaximumFractionDigits(0);

        this.view.onAdd(_ -> {
            modal.setModel(null);
            modal.setVisible(true);
        });

        this.view.onEdit(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < services.size()) {
                Service s = services.get(selectedRow);
                modal.setModel(s);
                modal.setVisible(true);
            }
        });

        this.view.onDelete(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < services.size()) {
                Service s = services.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to delete service: " + s.getName() + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new SwingWorker<Integer, Void>() {
                        @Override
                        protected Integer doInBackground() {
                            return service.delete(s.getId());
                        }

                        @Override
                        protected void done() {
                            try {
                                if (get() > 0) {
                                    view.showSuccessMessage(Constants.SuccessMessage.DELETE_SERVICE_SUCCESS);
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
            String query = view.getSearchQuery();
            if (query == null || query.isEmpty()) {
                view.setTableModel(new ServiceTableModel(services, vndFormat));
            } else {
                List<Service> filtered = services.stream()
                        .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()))
                        .toList();
                view.setTableModel(new ServiceTableModel(filtered, vndFormat));
            }
        });

        this.modal.onSave(_ -> {
            if (!modal.isValidInput()) {
                return;
            }

            Service s = modal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    try {
                        return s.getId() == null ? service.create(s) : service.update(s);
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get() > 0) {
                            if (s.getId() == null) {
                                view.showSuccessMessage(Constants.SuccessMessage.CREATE_SERVICE_SUCCESS);
                            } else {
                                view.showSuccessMessage(Constants.SuccessMessage.UPDATE_SERVICE_SUCCESS);
                            }
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

    public ServiceView getView() {
        return view;
    }

    public void loadData() {
        new SwingWorker<List<Service>, Void>() {
            @Override
            protected List<Service> doInBackground() {
                return service.findAll();
            }

            @Override
            protected void done() {
                try {
                    services = get();
                    view.setTableModel(new ServiceTableModel(services, vndFormat));
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }

    static class ServiceTableModel extends AbstractTableModel {
        private final String[] cols = { "Id", "Name", "Unit", "Price", "Description", "Status", "Created At",
                "Created By", "Updated At", "Updated By" };
        private final List<Service> data;

        private final NumberFormat vndFormat;

        ServiceTableModel(List<Service> data, NumberFormat vndFormat) {
            this.data = data;
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
            Service o = data.get(r);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getName();
                case 2 -> o.getUnit();
                case 3 -> vndFormat.format(o.getPrice());
                case 4 -> o.getDescription();
                case 5 -> o.getStatus();
                case 6 -> o.getCreatedAt() == null ? null
                        : o.getCreatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
                case 7 -> o.getCreatedBy();
                case 8 -> o.getUpdatedAt() == null ? null
                        : o.getUpdatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
                case 9 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}
