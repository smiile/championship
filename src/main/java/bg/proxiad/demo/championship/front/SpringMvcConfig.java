package bg.proxiad.demo.championship.front;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = SpringMvcConfig.class, useDefaultFilters = false, includeFilters = { @Filter(Controller.class), @Filter(Component.class) })
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

}
