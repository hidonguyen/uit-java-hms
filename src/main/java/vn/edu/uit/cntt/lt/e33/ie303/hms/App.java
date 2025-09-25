package vn.edu.uit.cntt.lt.e33.ie303.hms;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.AppConfig;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.LoginPresenter;
import com.formdev.flatlaf.FlatLightLaf;

import javax.sql.DataSource;
import javax.swing.SwingUtilities;

import org.flywaydb.core.Flyway;

public final class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();

            AppConfig cfg = AppConfig.load();
            DataSource ds = AppConfig.dataSource(cfg);

            // Run Flyway migrations on startup
            Flyway.configure()
                .dataSource(ds)
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .load()
                .migrate();

            LoginPresenter login = new LoginPresenter();
            login.show();
        });
    }
}