package com.ekocaman.microservices.account.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootApplication
@EntityScan("com.ekocaman.microservices.account.model")
@EnableJpaRepositories("com.ekocaman.microservices.account.repo")
@PropertySource("classpath:db-config.properties")
public class DbConfig {
    protected Logger logger = Logger.getLogger(DbConfig.class
            .getName());

    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        DataSource dataSource = (new EmbeddedDatabaseBuilder())
                .addScript("classpath:testdb/schema.sql")
                .addScript("classpath:testdb/data.sql").build();

        logger.info("dataSource = " + dataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> accounts = jdbcTemplate
                .queryForList("SELECT number FROM TABLE_ACCOUNT");
        logger.info("System has " + accounts.size() + " accounts");

        Random rand = new Random();

        for (Map<String, Object> item : accounts) {
            String number = (String) item.get("number");
            BigDecimal balance = new BigDecimal(rand.nextInt(10000000) / 100.0)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            jdbcTemplate.update(
                    "UPDATE TABLE_ACCOUNT SET balance = ? WHERE number = ?",
                    balance, number);
        }

        return dataSource;
    }
}
