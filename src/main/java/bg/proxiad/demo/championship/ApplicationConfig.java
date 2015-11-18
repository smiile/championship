package bg.proxiad.demo.championship;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import bg.proxiad.demo.championship.logic.LogicConfig;
import bg.proxiad.demo.championship.model.HibernateConfig;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration()
@PropertySource("classpath:application.properties")
@Import({HibernateConfig.class, LogicConfig.class})
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }
}
