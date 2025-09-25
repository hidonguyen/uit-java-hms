package vn.edu.uit.cntt.lt.e33.ie303.hms;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.AppConfig;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository.UserRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service.UserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.UserPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.IUserView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.UserView;
import com.formdev.flatlaf.FlatLightLaf;

import javax.sql.DataSource;
import javax.swing.SwingUtilities;

public final class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            AppConfig cfg = AppConfig.load();
            DataSource ds = AppConfig.dataSource(cfg);
            IUserRepository userRepository = new UserRepository(ds);
            IUserService service = new UserService(userRepository);
            IUserView view = new UserView();
            UserPresenter presenter = new UserPresenter(view, service);
            view.showWindow();
            presenter.load();
        });
    }
}