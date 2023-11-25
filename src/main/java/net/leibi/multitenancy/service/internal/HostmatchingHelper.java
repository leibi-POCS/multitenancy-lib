package net.leibi.multitenancy.service.internal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.leibi.multitenancy.config.Tenant;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HostmatchingHelper {

    static Optional<Tenant> getTenantFromList(String host, List<Tenant> list) {
        if (list.size() == 1) return Optional.of(list.get(0));
        logInfoAboutNotFoundHosts(host, list.size());
        return Optional.empty();
    }

    static void logInfoAboutNotFoundHosts(String host, int listSize) {
        if (listSize > 1) {
            log.warn("more than one tenant for name '{}' found", host);
        } else {
            log.info("No Tenant config for '{}' found", host);
        }
    }


}
