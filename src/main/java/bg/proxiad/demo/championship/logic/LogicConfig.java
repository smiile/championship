package bg.proxiad.demo.championship.logic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = LogicConfig.class, useDefaultFilters = false, includeFilters = @Filter(Service.class) )
public class LogicConfig {

}
