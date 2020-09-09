package com.nettm.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnMissingClass
@PropertySource("classpath:application.properties")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class MySQLAutoconfiguration {

    @Bean
    public MyConfig initBean() {
        return new MyConfig();
    }

}
