package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.user;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class UserView extends BaseTableView {

    private JButton resetPasswordBtn;

    @Override
    protected String getModuleName() {
        return "User";
    }

    @Override
    protected String getAddButtonText() {
        return "+ Add User";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search users...";
    }

    @Override
    protected String getErrorTitle() {
        return Constants.ErrorTitle.USER;
    }

    @Override
    protected void onInitExtraActions(JPanel actionPanel) {
        resetPasswordBtn = createModernButton("Reset Password", new Color(234, 179, 8), Color.WHITE);
        resetPasswordBtn.setEnabled(false);
        actionPanel.add(createSpacer(10));
        actionPanel.add(resetPasswordBtn);
    }

    @Override
    protected void onRowSelectionChanged(boolean rowSelected) {
        if (resetPasswordBtn != null) {
            resetPasswordBtn.setEnabled(rowSelected);
            resetPasswordBtn.setOpaque(rowSelected);
            resetPasswordBtn.repaint();
        }
    }

    private Component createSpacer(int width) {
        return javax.swing.Box.createHorizontalStrut(width);
    }

    public void onResetPassword(java.awt.event.ActionListener l) {
        if (resetPasswordBtn != null) {
            resetPasswordBtn.addActionListener(l);
        }
    }
}
