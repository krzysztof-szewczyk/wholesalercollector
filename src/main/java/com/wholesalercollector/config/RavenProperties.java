package com.wholesalercollector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wholesale.raven")
@Data
public class RavenProperties {
    String url;
}
