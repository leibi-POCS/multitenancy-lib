package net.leibi.multitenancylib.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MultitenancyPropertiesConfig.class)
@EnableConfigurationProperties
class MultitenancyPropertiesConfigTest {

    private final Tenant immenstaad = new Tenant("ImmenStaad", "Hallo Actico", "immenstaad.acs", "http://localhost:18080/auth/realms/immenstaad-realm");
    private final Tenant sofia = new Tenant("Sofia", "halloSofia", "sofia.acs", "http://localhost:18080/auth/realms/sofia-realm");
    @Autowired
    private MultitenancyPropertiesConfig multitenancyPropertiesConfig;

    @Test
    void testInstance() {
        assertThat(multitenancyPropertiesConfig).isNotNull();
    }

    @Test
    void getTenants() {
        assertThat(multitenancyPropertiesConfig.getTenants())
                .hasSize(2)
                .containsKeys("tenant1", "tenant3")
                .containsEntry("tenant1", immenstaad)
                .containsEntry("tenant3", sofia);
    }

    @Test
    void getInstance() {
        assertThat(multitenancyPropertiesConfig.getInstance()).isEqualTo("myInstance");
    }

}