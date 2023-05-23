package by.piskunou.solvdlaba.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

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
