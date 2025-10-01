package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.roomType;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class RoomTypeView extends BaseTableView {

    @Override
    protected String getModuleName() {
        return "Room Type";
    }

    @Override
    protected String getAddButtonText() {
        return "+ Room Type";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search room types...";
    }

    @Override
    protected String getErrorTitle() {
        return Constants.ErrorTitle.ROOM_TYPE;
    }
}