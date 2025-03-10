package com.example.domoycoursework.services

import com.example.domoycoursework.exceptions.FileException
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.springframework.web.multipart.MultipartFile
import java.util.*
import io.minio.http.Method
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class FileService(
    private var minioClient: MinioClient
) {

    fun saveFile(file: MultipartFile, bucketName: String): String {
        val fileName = UUID.randomUUID().toString() + file.originalFilename
        val inputStream = file.inputStream
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(fileName)
                    .stream(inputStream, file.size, -1)
                    .build()
            )
            println("File uploaded successfully to MinIO: $fileName")
            return fileName
        } catch (e: Exception) {
            throw FileException("Failed to upload file to MinIO")
        } finally {
            inputStream.close()
        }
    }

    fun removeFile(fileName: String, bucketName: String) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(fileName)
                    .build()
            )
            println("File deleted successfully from MinIO: $fileName")
        } catch (e: Exception) {
            throw FileException("Failed to delete file from MinIO")
        }
    }

    fun getPresignedUrl(fileName: String, bucketName: String): String {
        val args = GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket(bucketName)
            .`object`(fileName)
            .expiry(60, TimeUnit.MINUTES)
            .build()

        return minioClient.getPresignedObjectUrl(args)
    }
}

