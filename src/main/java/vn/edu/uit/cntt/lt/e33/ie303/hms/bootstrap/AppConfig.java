package vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {
    public final String dbUrl;
    public final String dbUser;
    public final String dbPassword;
    public final String aesHashKey;

    private AppConfig(String dbUrl, String dbUser, String dbPassword, String aesHashKey) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.aesHashKey = aesHashKey;
    }

    public static AppConfig load() {
        Properties p = new Properties();
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (in != null)
                p.load(in);
        } catch (IOException ignored) {
        }
        String url = getEnvOrDefault("DB_URL", p.getProperty("db.url", "jdbc:postgresql://localhost:5432/hms"));
        String user = getEnvOrDefault("DB_USER", p.getProperty("db.user", "postgres"));
        String pass = getEnvOrDefault("DB_PASSWORD", p.getProperty("db.password", "postgres"));
        String hashKey = getEnvOrDefault("AES_HASH_KEY", p.getProperty("aes.hashkey", "1234567890123456"));
        return new AppConfig(url, user, pass, hashKey);
    }

    private static String getEnvOrDefault(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isEmpty()) ? def : v;
    }

    public static DataSource dataSource(AppConfig cfg) {
        System.setProperty("user.timezone", "UTC");
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));

        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(cfg.dbUrl);
        hc.setUsername(cfg.dbUser);
        hc.setPassword(cfg.dbPassword);
        hc.setMaximumPoolSize(5);
        hc.setMinimumIdle(1);
        hc.setPoolName("hms-app-pool");

        hc.addDataSourceProperty("serverTimezone", "UTC");

        return new HikariDataSource(hc);
    }
}