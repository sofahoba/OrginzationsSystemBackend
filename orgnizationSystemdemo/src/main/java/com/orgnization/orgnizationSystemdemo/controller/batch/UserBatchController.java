package com.orgnization.orgnizationSystemdemo.controller.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/import")
public class UserBatchController {

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @PostMapping("/users")
    public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {
        JobParameters params = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .addString("fileName", file.getOriginalFilename())
                .toJobParameters();

        try {
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("my-users-", ".csv");
            file.transferTo(tempFile.toFile());

            System.setProperty("uploadedFile", tempFile.toAbsolutePath().toString());

            jobLauncher.run(importUserJob, params);
            return ResponseEntity.ok("Job started successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Job failed: " + e.getMessage());
        }
    }
}
