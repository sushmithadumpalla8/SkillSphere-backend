package com.skillsphere.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final Pattern POSTGRES_URI_PATTERN = Pattern.compile(
            "^(?:postgres|postgresql)://(?:([^:@]+)(?::([^@]+))?@)?([^/:?#]+)(?::(\\d+))?(/[^?#]*)?(?:\\?(.*))?$"
    );

    @Value("${DATABASE_URL:#{null}}")
    private String databaseUrlEnv;

    @Value("${DB_URL:#{null}}")
    private String dbUrlEnv;

    @Value("${spring.datasource.url:#{null}}")
    private String springDatasourceUrl;

    @Value("${spring.datasource.username:#{null}}")
    private String username;

    @Value("${spring.datasource.password:#{null}}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Priority: Check Render default environment variable DATABASE_URL, then DB_URL, then spring.datasource.url
        String rawUrl = null;
        if (StringUtils.hasText(databaseUrlEnv)) {
            rawUrl = databaseUrlEnv.trim();
        } else if (StringUtils.hasText(dbUrlEnv)) {
            rawUrl = dbUrlEnv.trim();
        } else if (StringUtils.hasText(springDatasourceUrl)) {
            rawUrl = springDatasourceUrl.trim();
        }

        if (StringUtils.hasText(rawUrl)) {
            // Check if URL uses postgres:// or postgresql:// scheme (standard on Render and cloud providers)
            if (rawUrl.startsWith("postgres://") || rawUrl.startsWith("postgresql://")) {
                log.info("Detected cloud PostgreSQL URI. Converting to JDBC URL...");
                HikariConfig config = buildPostgresConfigFromUri(rawUrl);
                if (config != null) {
                    return new HikariDataSource(config);
                }
            } else if (rawUrl.startsWith("jdbc:")) {
                log.info("Detected standard JDBC URL: {}", maskUrl(rawUrl));
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(rawUrl);
                if (StringUtils.hasText(username)) {
                    config.setUsername(username);
                }
                if (StringUtils.hasText(password)) {
                    config.setPassword(password);
                }
                if (rawUrl.contains("postgresql")) {
                    config.setDriverClassName("org.postgresql.Driver");
                    tuneHikariForCloud(config);
                }
                return new HikariDataSource(config);
            }
        }

        // Fallback to standard Spring Datasource properties
        log.info("Using default Spring DataSource configuration: {}", maskUrl(springDatasourceUrl));
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(springDatasourceUrl);
        if (StringUtils.hasText(username)) {
            config.setUsername(username);
        }
        if (StringUtils.hasText(password)) {
            config.setPassword(password);
        }
        return new HikariDataSource(config);
    }

    private HikariConfig buildPostgresConfigFromUri(String rawUrl) {
        String host = null;
        int port = 5432;
        String path = "/skillsphere";
        String query = null;
        String parsedUser = username;
        String parsedPass = password;

        try {
            URI uri = new URI(rawUrl);
            host = uri.getHost();
            if (uri.getPort() != -1) {
                port = uri.getPort();
            }
            if (StringUtils.hasText(uri.getPath())) {
                path = uri.getPath();
            }
            query = uri.getQuery();

            if (uri.getUserInfo() != null) {
                String[] userInfo = uri.getUserInfo().split(":", 2);
                parsedUser = userInfo[0];
                if (userInfo.length > 1) {
                    parsedPass = userInfo[1];
                }
            }
        } catch (URISyntaxException e) {
            log.warn("Standard URI parsing failed, attempting regex extraction: {}", e.getMessage());
            Matcher matcher = POSTGRES_URI_PATTERN.matcher(rawUrl);
            if (matcher.matches()) {
                parsedUser = matcher.group(1);
                parsedPass = matcher.group(2);
                host = matcher.group(3);
                String portStr = matcher.group(4);
                if (StringUtils.hasText(portStr)) {
                    try {
                        port = Integer.parseInt(portStr);
                    } catch (NumberFormatException ignored) {}
                }
                String pathStr = matcher.group(5);
                if (StringUtils.hasText(pathStr)) {
                    path = pathStr;
                }
                query = matcher.group(6);
            } else {
                log.error("Failed to parse PostgreSQL URL with regex fallback: {}", maskUrl(rawUrl));
                return null;
            }
        }

        if (!StringUtils.hasText(host)) {
            log.error("Unable to extract host from PostgreSQL URI");
            return null;
        }

        StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                .append(host)
                .append(":")
                .append(port)
                .append(path);

        // Ensure SSL is enabled for external Render connections
        boolean hasQuery = StringUtils.hasText(query);
        if (hasQuery) {
            jdbcUrl.append("?").append(query);
            if (host.contains("render.com") && !query.contains("sslmode")) {
                jdbcUrl.append("&sslmode=require");
            }
        } else if (host.contains("render.com")) {
            jdbcUrl.append("?sslmode=require");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl.toString());
        if (StringUtils.hasText(parsedUser)) {
            config.setUsername(parsedUser);
        }
        if (StringUtils.hasText(parsedPass)) {
            config.setPassword(parsedPass);
        }
        config.setDriverClassName("org.postgresql.Driver");
        tuneHikariForCloud(config);

        log.info("Successfully configured PostgreSQL DataSource for host: {}:{}", host, port);
        return config;
    }

    private void tuneHikariForCloud(HikariConfig config) {
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setIdleTimeout(300000); // 5 minutes
        config.setMaxLifetime(600000); // 10 minutes
        config.setConnectionTimeout(30000); // 30 seconds
        config.setKeepaliveTime(60000); // 1 minute
    }

    private String maskUrl(String url) {
        if (!StringUtils.hasText(url)) return "null";
        return url.replaceAll("://([^:]+):([^@]+)@", "://$1:****@");
    }
}
