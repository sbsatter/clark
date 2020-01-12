package com.sbsatter.eventprocessor.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbsatter.eventprocessor.model.Event;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.GsonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

//    @Bean
//    public FlatFileItemReader<Event> reader() {
//        return new FlatFileItemReaderBuilder<Event>().build();
//
//    }

    @Bean
    public JsonItemReader<Event> jsonItemReader() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss z").create();
        GsonJsonObjectReader<Event> gsonMapper = new GsonJsonObjectReader<>(Event.class);
        gsonMapper.setMapper(gson);
        return new JsonItemReaderBuilder<Event>()
                .jsonObjectReader(gsonMapper)
                .resource(new FileSystemResource("events-line.json"))
                .name("eventsJsonItemReader")
                .build();
    }

    @Bean
    public Job executeStep1(Step step) {
        return jobBuilderFactory.get("job-execute-1")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter<Event> writer) {
        return stepBuilderFactory.get("execute-1")
                .<Event,Event>chunk(100)
                .reader(jsonItemReader())
                .processor(new EventToObjectMappingProcessor())
                .writer(writer)
                .build();
    }

//    @Bean
//    public JdbcBatchItemWriter<Event> writer(final DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Event>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO event (aggregate_id, timestamp) VALUES (:aggregate_id, :timestamp)")
//                .dataSource(dataSource)
//                .build();
//    }

    @Bean
    public FlatFileItemWriter<Event> writer()
    {


        //Create writer instance
        FlatFileItemWriter<Event> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(new FileSystemResource("events.csv"));

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties 
        writer.setLineAggregator(new DelimitedLineAggregator<Event>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Event>() {
                    {
                        setNames(new String[] { "aggregate_id", "timestamp" });
                    }
                });
            }
        });
        return writer;
    }

}
