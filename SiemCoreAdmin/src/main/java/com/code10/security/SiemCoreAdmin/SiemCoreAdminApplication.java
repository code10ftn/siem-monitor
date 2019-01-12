package com.code10.security.SiemCoreAdmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SiemCoreAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiemCoreAdminApplication.class, args);
	}
}
