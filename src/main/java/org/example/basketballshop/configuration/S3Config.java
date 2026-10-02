package org.example.basketballshop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${storage.s3.endpoint}")
    private String endpoint;

    @Value("${storage.s3.access}")
    private String accessKey;

    @Value("${storage.s3.secret}")
    private String secretKey;

    @Bean
    S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_CENTRAL_1)
                .serviceConfiguration(
                        S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build()
                )
                .build();
    }

}
