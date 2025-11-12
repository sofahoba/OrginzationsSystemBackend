package com.orgnization.orgnizationSystemdemo.config.configrations;

import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.orgnization.orgnizationSystemdemo.dto.csv.UserCSV;
import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.repository.user.UserRepository;


@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository userRepository;
    private final UserProcessorComponent userProcessorComponent;

    @Bean
    @StepScope
    public FlatFileItemReader<UserCSV> itemReader(@Value("#{jobParameters['fileName']}") String fileName) {
        FlatFileItemReader<UserCSV> reader = new FlatFileItemReader<>();

        // نقرا الملف اللي اتحفظ مؤقت
        String path = System.getProperty("uploadedFile");
        reader.setResource(new FileSystemResource(path));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean 
    public RepositoryItemWriter<User> itemWriter() {
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }
    
    @Bean
    public Step importStep() {
        return new StepBuilder("csvImport", jobRepository)
                .<UserCSV, User>chunk(10, transactionManager)
                .reader(itemReader(null))
                .processor(userProcessorComponent)
                .writer(itemWriter())
                .build();
    }
    
    @Bean
    public Job runJob() {
        return new JobBuilder("importUsers", jobRepository)
                .start(importStep())
                .build();
    }
    
    private LineMapper<UserCSV> lineMapper() {
        DefaultLineMapper<UserCSV> lineMapper = new DefaultLineMapper<>();
        
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("email", "password", "firstName", "lastName", "active", "type", "department_id");
        
        BeanWrapperFieldSetMapper<UserCSV> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserCSV.class);
        
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        
        return lineMapper;
    }
}