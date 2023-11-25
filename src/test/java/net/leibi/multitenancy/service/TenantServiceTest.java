package net.leibi.multitenancy.service;

import net.leibi.multitenancy.config.MultitenancyPropertiesConfig;
import net.leibi.multitenancy.config.Tenant;
import net.leibi.multitenancy.service.internal.DefaultTenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TenantServiceTest {

    private MultitenancyPropertiesConfig multitenancyPropertiesConfig;
    private TenantService tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        multitenancyPropertiesConfig = mock(MultitenancyPropertiesConfig.class);
        tenantService = new DefaultTenantService(multitenancyPropertiesConfig);
    }

    @Test
    void getCurrentTenant() {
        assertThat(tenantService.getCurrentTenant())
                .isNotNull()
                .isEqualTo(new Tenant("fallback","hallo fallback","",""));

    }

    @Test
    void setCurrentTenant() {
    }

    @Test
    void getTenantMatchingHost() {
    }

    @Test
    void getAllTenants() {
    }
}