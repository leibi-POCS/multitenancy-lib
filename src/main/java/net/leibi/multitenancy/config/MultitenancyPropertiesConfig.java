package net.leibi.multitenancy.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.Map;

@ConfigurationProperties(prefix = "leibi")
@PropertySource("classpath:/mt.properties")
@ConditionalOnResource(resources = "classpath:/mt.properties")
@Configuration
@Data
@Slf4j
public class MultitenancyPropertiesConfig {
  private Map<String, Tenant> tenants = Collections.emptyMap();
  private String instance;
}
