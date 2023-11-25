package net.leibi.multitenancy.config;

public record Tenant(String name, String message, String host, String issuerUri) {
}
