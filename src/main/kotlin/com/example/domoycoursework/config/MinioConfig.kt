package com.example.domoycoursework.config

import io.github.cdimascio.dotenv.Dotenv
import io.minio.*
import io.minio.messages.DeleteObject
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class MinioConfig {
    //TODO: change to System.getenv("...")
    private var dotenv = Dotenv.load()
    private var minioUrl = dotenv["MINIO_URL"]
    private var accessKey = dotenv["MINIO_ACCESS_KEY"]
    private var secretKey = dotenv["MINIO_SECRET_KEY"]

    @Bean
    @Lazy
    fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint(minioUrl)
            .credentials(accessKey, secretKey)
            .build()
    }

}
