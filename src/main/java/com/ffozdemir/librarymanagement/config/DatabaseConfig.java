package com.ffozdemir.librarymanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        try {
            logger.info("Veritabanı URL'si işleniyor: {}", databaseUrl);
            URI dbUri = new URI(databaseUrl);

            String userInfo = dbUri.getUserInfo();
            if (userInfo == null) {
                throw new RuntimeException("DATABASE_URL format hatası: Kullanıcı bilgisi bulunamadı");
            }

            String[] credentials = userInfo.split(":");
            if (credentials.length != 2) {
                throw new RuntimeException("DATABASE_URL format hatası: Kullanıcı bilgisi yanlış formatta");
            }

            String username = credentials[0];
            String password = credentials[1];
            String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

            logger.info("JDBC URL: {} (kullanıcı: {})", jdbcUrl, username);

            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (URISyntaxException e) {
            logger.error("DATABASE_URL ayrıştırma hatası", e);
            throw new RuntimeException("DATABASE_URL geçersiz format: " + e.getMessage(), e);
        }
    }
}