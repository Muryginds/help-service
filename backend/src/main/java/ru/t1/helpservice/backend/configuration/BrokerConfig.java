package ru.t1.helpservice.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.t1.helpservice.backend.configuration.properties.KafkaProperties;
import ru.t1.helpservice.backend.dto.SupportPhraseRequestDto;
import ru.t1.helpservice.backend.repository.SupportPhraseRepository;
import ru.t1.helpservice.backend.service.BrokerMessageConsumer;
import ru.t1.helpservice.backend.service.SupportPhraseService;
import ru.t1.helpservice.backend.service.impl.KafkaSupportPhraseServiceImpl;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@EnableConfigurationProperties(KafkaProperties.class)
@ConditionalOnProperty(prefix = "application.broker", name = "enable", havingValue = "true")
@Configuration
@RequiredArgsConstructor
public class BrokerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    @Primary
    public SupportPhraseService supportPhraseService(
            SupportPhraseRepository supportPhraseRepository,
            KafkaTemplate<String, SupportPhraseRequestDto> phraseRequestkafkaTemplate,
            KafkaProperties kafkaProperties) {
        return new KafkaSupportPhraseServiceImpl(
                supportPhraseRepository, phraseRequestkafkaTemplate, kafkaProperties);
    }

    @Bean
    public BrokerMessageConsumer brokerMessageConsumer(SupportPhraseRepository supportPhraseRepository) {
        return new BrokerMessageConsumer(supportPhraseRepository);
    }

    @Bean
    public ProducerFactory<String, SupportPhraseRequestDto> phraseRequestProducerFactory() {
        var configProps = new HashMap<String, Object>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers());
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, SupportPhraseRequestDto> phraseRequestkafkaTemplate(
            ProducerFactory<String, SupportPhraseRequestDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, SupportPhraseRequestDto> consumerFactory() {
        var configProps = new HashMap<String, Object>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.consumer().groupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(
                configProps, new StringDeserializer(), new JsonDeserializer<>(SupportPhraseRequestDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SupportPhraseRequestDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, SupportPhraseRequestDto> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, SupportPhraseRequestDto>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
