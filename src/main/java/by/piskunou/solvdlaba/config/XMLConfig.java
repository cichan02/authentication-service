package by.piskunou.solvdlaba.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@Slf4j
public class XMLConfig {

    @Bean
    public XML producerXml() throws FileNotFoundException {
        File file = new File("/kafka/producer.xml");
        log.info( Boolean.toString(file.exists()) );
        return new XMLDocument(new File("/Users/solvd/IdeaProjects/micro-airport/authentication-service/src/main/resources/kafka/producer.xml"));
    }

}
