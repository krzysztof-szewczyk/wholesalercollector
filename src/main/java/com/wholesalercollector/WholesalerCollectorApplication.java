package com.wholesalercollector;

import com.wholesalercollector.config.CragProperties;
import com.wholesalercollector.config.KolbaProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.wholesalercollector.config.RavenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableEncryptableProperties
@EnableConfigurationProperties({KolbaProperties.class, RavenProperties.class, CragProperties.class})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WholesalerCollectorApplication {

	public static void main(String[] args) {

		SpringApplication.run(WholesalerCollectorApplication.class, args);
	}

}
