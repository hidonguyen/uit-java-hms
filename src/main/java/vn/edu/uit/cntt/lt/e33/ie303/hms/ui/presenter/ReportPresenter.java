package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportBookingCountPoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportDateRangeParams;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportGuestMix;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportKpiSummary;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRevenueByRoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRoomRevenuePoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportServiceRevenueItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IReportService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.report.ReportsView;

public class ReportPresenter {
    private final ReportsView view;
    private final IReportService service;
    private ReportDateRangeParams currentParams;

    public ReportPresenter(JFrame parentFrame) {
        this.view = new ReportsView();
        this.service = DIContainer.getInstance().getReportService();
        this.view.onFilterDateRange(params -> {
            this.currentParams = params;
            loadData();
        });
        this.currentParams = ReportDateRangeParams.last7Days();
        this.view.setFilterRange(currentParams);
        loadData();
    }

    public ReportsView getView() {
        return view;
    }

    private void loadData() {
        new SwingWorker<ReportKpiSummary, Void>() {
            @Override
            protected ReportKpiSummary doInBackground() {
                return service.getKpi(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setKpiSummary(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();

        new SwingWorker<List<ReportRoomRevenuePoint>, Void>() {
            @Override
            protected List<ReportRoomRevenuePoint> doInBackground() {
                return service.getRoomRevenue(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setRoomRevenueData(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();

        new SwingWorker<List<ReportBookingCountPoint>, Void>() {
            @Override
            protected List<ReportBookingCountPoint> doInBackground() {
                return service.getOccupancy(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setBookingCountData(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();

        new SwingWorker<List<ReportRevenueByRoomType>, Void>() {
            @Override
            protected List<ReportRevenueByRoomType> doInBackground() {
                return service.getRevenueByRoomType(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setRevenueByRoomTypeData(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();

        new SwingWorker<List<ReportServiceRevenueItem>, Void>() {
            @Override
            protected List<ReportServiceRevenueItem> doInBackground() {
                return service.getServiceRevenue(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setServiceRevenueData(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();

        new SwingWorker<List<ReportGuestMix>, Void>() {
            @Override
            protected List<ReportGuestMix> doInBackground() {
                return service.getGuestMix(currentParams);
            }

            @Override
            protected void done() {
                try {
                    view.setGuestMixData(get());
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }
}
