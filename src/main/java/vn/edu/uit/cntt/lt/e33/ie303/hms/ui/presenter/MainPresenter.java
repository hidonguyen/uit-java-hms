package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.LoginView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.MainView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ApiException;

import javax.swing.*;

public class MainPresenter {
    private final MainView mainView;
    private final LoginView loginView;

    private final IUserService userService;

    private User loggedInUser;

    public MainPresenter() {
        this.mainView = new MainView();

        if (this.loggedInUser == null) {
            this.loginView = new LoginView();
            this.userService = DIContainer.getInstance().getUserService();

            this.loginView.onSubmit(_ -> login());

            this.loginView.setVisible(true);
        } else {
            this.loginView = null;
            this.userService = null;

            this.mainView.setLoggedInUser(this.loggedInUser);
            this.mainView.setDatabaseStatus(true); // Assume connected
            this.mainView.setVisible(true);
        }
    }

    private void login() {
        new SwingWorker<User, Void>() {
            @Override protected User doInBackground() {
                try {
                    return userService.login(loginView.getUsername(), loginView.getPassword());
                } catch (ApiException e) {
                    JOptionPane.showMessageDialog(loginView, e.getError().getMessage(), e.getError().getTitle(), JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
            @Override protected void done() {
                try {
                    User user = get();

                    if (user != null) {
                        loggedInUser = user;

                        loginView.setVisible(false);
                        loginView.clearFields();

                        mainView.setLoggedInUser(loggedInUser);
                        mainView.setDatabaseStatus(true); // Assume connected
                        mainView.setVisible(true);
                    }
                } catch (Exception e) {
                }
            }
        }.execute();
    }
}