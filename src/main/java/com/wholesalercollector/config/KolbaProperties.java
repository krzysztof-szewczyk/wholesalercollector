package com.wholesalercollector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wholesale.kolba.login")
@Data
public class KolbaProperties {
    String email;
    String password;
}
