package net.leibi.multitenancylib.service;


import net.leibi.multitenancylib.config.Tenant;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TenantService {

    String DEFAULT_TENANT_NAME = "fallback";
    String HALLO_DEFAULT = "hallo fallback";

    @NonNull
    Tenant getCurrentTenant();

    void setCurrentTenant(@NonNull Tenant tenant);

    @NonNull
    Optional<Tenant> getTenantMatchingHost(@NonNull String host);

    List<Tenant> getAllTenants();
}
