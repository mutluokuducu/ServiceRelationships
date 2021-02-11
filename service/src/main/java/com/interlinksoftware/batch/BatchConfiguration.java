package com.interlinksoftware.batch;

import com.interlinksoftware.processor.ServiceModelProcessor;
import com.interlinksoftware.repository.entity.ServiceRelationships;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired
  public DataSource dataSource;
  @Autowired
  private JobBuilderFactory jobBuilders;
  @Autowired
  private StepBuilderFactory stepBuilders;

  @Bean
  public Job jobProcess(NotificationListener notificationListener, Step step) {
    return jobBuilders.get("jobProcess")
        .listener(notificationListener)
        .start(step)
        .build();
  }

  @Bean
  public Step step(ItemReader<ServiceRelationships> reader) {
    return stepBuilders.get("step")
        .<ServiceRelationships, ServiceRelationships>chunk(10)
        .reader(reader)
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<ServiceRelationships> reader(
      @Value("#{jobParameters[pathToFile]}") String pathToFile) {

    if (pathToFile.startsWith("http")) {
      ResourceLoader resourceLoader = new DefaultResourceLoader();
      Resource resource = resourceLoader.getResource(pathToFile);

      return new FlatFileItemReaderBuilder<ServiceRelationships>()
          .linesToSkip(1)
          .name("personItemReader")
          .resource(resource)
          .delimited()
          .names(new String[]{"parent", "child", "label", "impact"})
          .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
            setTargetType(ServiceRelationships.class);
          }})
          .build();
    }

    return new FlatFileItemReaderBuilder<ServiceRelationships>()
        .linesToSkip(1)
        .name("personItemReader")
        .resource(new ClassPathResource(pathToFile))
        .delimited()
        .names(new String[]{"parent", "child", "label", "impact"})
        .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
          setTargetType(ServiceRelationships.class);
        }})
        .build();
  }

  @Bean
  public ServiceModelProcessor processor() {
    return new ServiceModelProcessor();
  }

  @Bean
  public ItemWriter<ServiceRelationships> writer() {
    return new JdbcBatchItemWriterBuilder<ServiceRelationships>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql(
            "INSERT INTO service_relationships (parent, child, label, impact) VALUES (:parent, :child, :label, :impact)")
        .dataSource(dataSource)
        .build();
  }
}
