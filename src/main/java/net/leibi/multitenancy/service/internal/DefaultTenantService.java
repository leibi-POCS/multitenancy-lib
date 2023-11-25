package net.leibi.multitenancy.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.leibi.multitenancy.config.MultitenancyPropertiesConfig;
import net.leibi.multitenancy.config.Tenant;
import net.leibi.multitenancy.service.TenantService;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static net.leibi.multitenancy.service.internal.HostmatchingHelper.getTenantFromList;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultTenantService implements TenantService {

    private final ThreadLocal<Tenant> currentTenant = ThreadLocal.withInitial(() -> new Tenant(DEFAULT_TENANT_NAME, HALLO_DEFAULT, "", ""));

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
        return getTenantFromList(host, getTenantsMatchingHost(host));
    }

    @Override
    public List<Tenant> getAllTenants() {
        return multitenancyPropertiesConfig.getTenants().values().stream().toList();
    }

    @Override
    public void unsetTenant() {
        currentTenant.remove();
    }

    private List<Tenant> getTenantsMatchingHost(String host) {
        return getAllTenants().stream().filter(tenant -> tenant.host().equalsIgnoreCase(host)).toList();
    }
}
