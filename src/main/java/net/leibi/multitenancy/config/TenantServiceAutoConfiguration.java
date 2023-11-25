package net.leibi.multitenancy.config;


import lombok.extern.slf4j.Slf4j;
import net.leibi.multitenancy.service.TenantService;
import net.leibi.multitenancy.service.internal.DefaultTenantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
//@ConditionalOnMissingBean(TenantService.class)
public class TenantServiceAutoConfiguration {
    @Bean
    public TenantService tenantService(MultitenancyPropertiesConfig multitenancyPropertiesConfig) {
        log.info(
                "Using DefaultTenantservice. If this is not working for you, consider implementing a custom Tenantservice.");
        return new DefaultTenantService(multitenancyPropertiesConfig);
    }

}
