package by.piskunou.solvdlaba.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Configuration
public class XMLConfig {

    @Bean
    public XML producerXml() throws FileNotFoundException {
        return new XMLDocument( ResourceUtils.getFile("classpath:kafka/producer.xml") );
    }

}
