package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class ServiceView extends BaseTableView {

    @Override
    protected String getModuleName() {
        return "Service";
    }

    @Override
    protected String getAddButtonText() {
        return "+ Add Service";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search services...";
    }

    @Override
    protected String getErrorTitle() {
        return Constants.ErrorTitle.SERVICE;
    }
}
