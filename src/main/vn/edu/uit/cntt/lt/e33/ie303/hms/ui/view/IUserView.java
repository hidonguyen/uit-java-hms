package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.table.TableModel;
import java.awt.event.ActionListener;

public interface IUserView {
    void setTableModel(TableModel model);
    void onRefresh(ActionListener l);
    void showError(String message);
    void showWindow();
}