package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.guest;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class GuestView extends BaseTableView {

    @Override
    protected String getModuleName() {
        return "Guest";
    }

    @Override
    protected String getAddButtonText() {
        return "+ Add Guest";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search guests...";
    }

    @Override
    protected String getErrorTitle() {
        return Constants.ErrorTitle.GUEST;
    }
}
