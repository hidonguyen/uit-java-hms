package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.LoginView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ApiException;

import javax.swing.*;

public class LoginPresenter {
    private final LoginView view;
    private final IUserService service;

    public LoginPresenter() {
        this.view = new LoginView();
        this.service = DIContainer.getInstance().getUserService();

        this.view.onSubmit(_ -> login());
    }

    private void login() {
        new SwingWorker<User, Void>() {
            @Override protected User doInBackground() {
                try {
                    return service.login(view.getUsername(), view.getPassword());
                } catch (ApiException e) {
                    return null;
                }
            }
            @Override protected void done() {
                try {
                    User user = get();

                    if (user == null) {
                        JOptionPane.showMessageDialog(view, "Đăng nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    view.showMainView();
                } catch (Exception e) {
                }
            }
        }.execute();
    }

    public void show() {
        view.setVisible(true);
    }
}