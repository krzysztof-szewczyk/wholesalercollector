package com.wholesalercollector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wholesale.crag")
@Data
public class CragProperties {
    String url;
}
