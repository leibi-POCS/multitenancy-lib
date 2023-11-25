package net.leibi.multitenancylib.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.leibi.multitenancylib.config.MultitenancyPropertiesConfig;
import net.leibi.multitenancylib.config.Tenant;
import net.leibi.multitenancylib.service.TenantService;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultTenantService implements TenantService {

    private final ThreadLocal<Tenant> currentTenant =
            ThreadLocal.withInitial(() -> new Tenant(DEFAULT_TENANT_NAME, HALLO_DEFAULT, "", ""));

    private final MultitenancyPropertiesConfig multitenancyPropertiesConfig;

    @Override
    @NonNull
    public Tenant getCurrentTenant() {
        return currentTenant.get();
    }

    @Override
    public void setCurrentTenant(@NonNull Tenant tenant) {
        currentTenant.remove();
        currentTenant.set(tenant);
        MDC.put("tenant", tenant.name());
    }

    @Override
    @NonNull
    public Optional<Tenant> getTenantMatchingHost(@NonNull String host) {
        if (host.equalsIgnoreCase("localhost")) return Optional.empty();
        List<Tenant> list = getTenantsMatchingHost(host);
        if (list.size() > 1) {
            log.warn("more than one tenant for name '{}' found", host);
        }
        if (list.size() == 1) return Optional.of(list.get(0));
        log.info("No Tenant config for '{}' found", host);
        return Optional.empty();
    }

    @Override
    public List<Tenant> getAllTenants() {
        return multitenancyPropertiesConfig.getTenants().values().stream().toList();
    }

    private List<Tenant> getTenantsMatchingHost(String host) {
        return multitenancyPropertiesConfig.getTenants().values().stream()
                .filter(tenant -> tenant.host().equalsIgnoreCase(host))
                .toList();
    }
}
