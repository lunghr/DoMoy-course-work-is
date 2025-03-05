package com.example.domoycoursework.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfig {

    @Bean
    fun flyway(dataSource: DataSource): Flyway {
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .cleanDisabled(false)
            .load()

        flyway.clean()   // Очищает все таблицы
        flyway.migrate() // Применяет миграции

        return flyway
    }
}
