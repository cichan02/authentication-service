package by.piskunou.solvdlaba.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }

    @Bean
    public NewTopic sendMailTopic() {
        return TopicBuilder.name("sendEmail")
                .partitions(6)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic updatePasswordTopic() {
        return TopicBuilder.name("updatePassword")
                .partitions(6)
                .replicas(1)
                .build();
    }

}