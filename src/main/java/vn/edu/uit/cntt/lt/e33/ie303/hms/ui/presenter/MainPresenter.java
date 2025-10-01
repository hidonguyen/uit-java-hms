package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.LoginView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.MainView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ApiException;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class MainPresenter {
    private final MainView mainView;
    private final LoginView loginView;

    private final IUserService userService;

    public MainPresenter() {
        this.mainView = new MainView();

        this.loginView = new LoginView();
        this.userService = DIContainer.getInstance().getUserService();

        this.loginView.onSubmit(_ -> login());

        this.loginView.setVisible(true);
        // login();
    }

    private void login() {
        new SwingWorker<User, Void>() {
            @Override protected User doInBackground() {
                try {
                    // For testing purpose, auto-login as manager
                    return userService.login("manager", "manager");
                    
                    // return userService.login(loginView.getUsername(), loginView.getPassword());
                } catch (ApiException e) {
                    JOptionPane.showMessageDialog(loginView, e.getError().getMessage(), e.getError().getTitle(), JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
            @Override protected void done() {
                try {
                    User user = get();

                    if (user != null) {
                        LoggedInUser.ID = user.getId();
                        LoggedInUser.USERNAME = user.getUsername();
                        LoggedInUser.ROLE = user.getRole();

                        loginView.setVisible(false);
                        loginView.clearFields();

                        mainView.setLoggedInUser(user);
                        mainView.setVisible(true);
                    }
                } catch (Exception e) {
                }
            }
        }.execute();
    }
}