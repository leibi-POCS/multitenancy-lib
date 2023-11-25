package net.leibi.multitenancy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LibVersionPrinter {

    public LibVersionPrinter() {
        log.info("{}: {}", getName(), getVersion());
    }

    public String getName() {
        return this.getClass().getPackage().getImplementationTitle();
    }

    public String getVersion() {
        return this.getClass().getPackage().getImplementationVersion();
    }
}
