package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.room;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class RoomView extends BaseTableView {

    @Override
    protected String getModuleName() {
        return "Room";
    }

    @Override
    protected String getAddButtonText() {
        return "+ Add Room";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search rooms...";
    }

    @Override
    protected String getErrorTitle() {
        return Constants.ErrorTitle.ROOM;
    }
}