package by.piskunou.solvdlaba.config;

import by.piskunou.solvdlaba.domain.SendEmailEvent;
import by.piskunou.solvdlaba.domain.UpdatePasswordEvent;
import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaSenderConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final XML settings;

    public Map<String, Object> properties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, new TextXpath(this.settings, "//groupId").toString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, new TextXpath(this.settings, "//keyDeserializer").toString());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, new TextXpath(this.settings, "//maxPartitionFetchBytesConfig").toString());
        return props;
    }

    @Bean
    public SenderOptions<String, SendEmailEvent> sendEmailEventOptions() {
        return SenderOptions.create(properties());
    }

    @Bean
    public SenderOptions<String, UpdatePasswordEvent> updatePasswordEventOptions() {
        return SenderOptions.create(properties());
    }

    @Bean
    public KafkaSender<String, SendEmailEvent> sendEmailSender() {
        return KafkaSender.create(sendEmailEventOptions());
    }

    @Bean
    public KafkaSender<String, UpdatePasswordEvent> updatePasswordSender() {
        return KafkaSender.create(updatePasswordEventOptions());
    }

}
