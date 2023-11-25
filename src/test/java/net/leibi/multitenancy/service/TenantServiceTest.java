package net.leibi.multitenancy.service;

import net.leibi.multitenancy.config.MultitenancyPropertiesConfig;
import net.leibi.multitenancy.config.Tenant;
import net.leibi.multitenancy.service.internal.DefaultTenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TenantServiceTest {

    public static final Tenant DEFAULT_TENANT = new Tenant("fallback", "hallo fallback", "", "");
    private final Tenant immenstaad = new Tenant("ImmenStaad", "Hallo Actico", "immenstaad.acs", "http://localhost:18080/auth/realms/immenstaad-realm");
    private final Tenant sofia = new Tenant("Sofia", "halloSofia", "sofia.acs", "http://localhost:18080/auth/realms/sofia-realm");
    private MultitenancyPropertiesConfig multitenancyPropertiesConfig;
    private TenantService tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        multitenancyPropertiesConfig = mock(MultitenancyPropertiesConfig.class);
        tenantService = new DefaultTenantService(multitenancyPropertiesConfig);

        when(multitenancyPropertiesConfig.getTenants())
                .thenReturn(Map.of("tenant1", immenstaad, "tenant3", sofia));

        tenantService.unsetTenant();
    }

    @Test
    void getCurrentTenant() {
        assertThat(tenantService.getCurrentTenant())
                .isNotNull()
                .isEqualTo(DEFAULT_TENANT);

    }

    @Test
    void setCurrentTenant() {
        assertThat(tenantService.getCurrentTenant()).isEqualTo(DEFAULT_TENANT);
        var t = new Tenant("foo", "bar", "baz", "uri");
        assertThatCode(() ->
                tenantService.setCurrentTenant(t)).doesNotThrowAnyException();

        assertThat(tenantService.getCurrentTenant()).isEqualTo(t);
    }

    @Test
    void getTenantMatchingHost() {

        assertThat(tenantService.getTenantMatchingHost("someHost")).isEmpty();
        assertThat(tenantService.getTenantMatchingHost("immenstaad.acs")).hasValue(immenstaad);

    }

    @Test
    void getAllTenants() {
        assertThat(tenantService.getAllTenants())
                .hasSize(2)
                .containsExactlyInAnyOrder(immenstaad, sofia);

    }
}