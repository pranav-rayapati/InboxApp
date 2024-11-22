package com.inbox.config;

import java.nio.file.Path;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataStaxConfig {

	@Bean
	CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties dataStaxAstraProperties) {

		/*
		 * return builder -> builder.withCloudSecureConnectBundle(Paths.get(
		 * "/Users/pranavrayapati/Desktop/Spring/inbox/src/main/resources/secure-connect-inbox-app.zip"
		 * )) .withAuthCredentials("qGWpmoOZPHyeoPqclhMzCiPk",
		 * "QI2FZtu-DPpZZuC_kg9S_-2iM1-xoZHTnh,T3E-EGSWOoRaB3vgDj1dhSsLNJmi4fU0tnz.9Ihi9au5zYZuNIvxJDsIaULcRuyuZK56DAKsFStjmk+YC.,xEOmU+S9Yq")
		 * .build();
		 */

		Path bundle = dataStaxAstraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);

	}

}
