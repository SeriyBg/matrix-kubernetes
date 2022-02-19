package com.matrix.k8s.merovingian;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@SpringBootApplication
public class MerovingianApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerovingianApplication.class, args);
	}

	@Bean
	@Profile("local")
	public ApiClient apiLocalClient() throws IOException {
		return Config.defaultClient();
	}

	@Bean
	@Profile("!local")
	public ApiClient apiClusterClient() throws IOException {
		return ClientBuilder.cluster().build();
	}

	@Bean
	public CoreV1Api coreV1Api(ApiClient client) {
		Configuration.setDefaultApiClient(client);
		return new CoreV1Api();
	}
}
