package com.inbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()

public class InboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(InboxApplication.class, args);
		/*
		 * // Create the CqlSession object: try (CqlSession session =
		 * CqlSession.builder() .withCloudSecureConnectBundle())
		 * .withAuthCredentials("qGWpmoOZPHyeoPqclhMzCiPk",
		 * "QI2FZtu-DPpZZuC_kg9S_-2iM1-xoZHTnh,T3E-EGSWOoRaB3vgDj1dhSsLNJmi4fU0tnz.9Ihi9au5zYZuNIvxJDsIaULcRuyuZK56DAKsFStjmk+YC.,xEOmU+S9Yq")
		 * .build()) { // Select the release_version from the system.local table:
		 * ResultSet rs = session.execute("select release_version from system.local");
		 * Row row = rs.one(); //Print the results of the CQL query to the console: if
		 * (row != null) { System.out.println(row.getString("release_version")); } else
		 * { System.out.println("An error occurred."); } } System.exit(0);
		 */
	}

}
