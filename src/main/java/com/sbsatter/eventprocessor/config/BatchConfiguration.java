package com.sbsatter.eventprocessor.config;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbsatter.eventprocessor.model.CompositeModel;
import com.sbsatter.eventprocessor.model.Event;
import com.sbsatter.eventprocessor.service.CompositeModelService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.PropertyExtractingDelegatingItemWriter;
import org.springframework.batch.item.json.GsonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final EventToCompositeProcessor processor;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CompositeModelService compositeModelService;

    @Value("${args-to-use}")
    private String [] argsToUse;

    @Autowired
    public BatchConfiguration(EventToCompositeProcessor processor, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CompositeModelService compositeModelService) {
        this.processor = processor;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.compositeModelService = compositeModelService;
    }

    @Bean
    public JsonItemReader<Event> jsonItemReader() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss z")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        GsonJsonObjectReader<Event> gsonMapper = new GsonJsonObjectReader<>(Event.class);
        gsonMapper.setMapper(gson);
        return new JsonItemReaderBuilder<Event>()
                .jsonObjectReader(gsonMapper)
                .resource(new FileSystemResource("events-single.json"))
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
    public Step step(PropertyExtractingDelegatingItemWriter<CompositeModel> writer, JsonItemReader<Event> jsonItemReader) {
        return stepBuilderFactory.get("execute-1")
                .<Event, CompositeModel>chunk(100)
                .reader(jsonItemReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public PropertyExtractingDelegatingItemWriter<CompositeModel> propertyExtractingDelegatingItemWriter() {
        PropertyExtractingDelegatingItemWriter<CompositeModel> writer = new PropertyExtractingDelegatingItemWriter<>();
        writer.setFieldsUsedAsTargetMethodArguments(argsToUse);
        writer.setTargetObject(compositeModelService);
        writer.setTargetMethod("decide");
        return writer;
    }

}
