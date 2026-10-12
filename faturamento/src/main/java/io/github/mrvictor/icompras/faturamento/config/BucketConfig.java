package io.github.mrvictor.icompras.faturamento.config;

import io.github.mrvictor.icompras.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BucketConfig {

    private final MinioProps minioProps;

    @Bean
    public MinioClient bucketClient() {
        return MinioClient
                .builder()
                .endpoint(minioProps.getUrl())
                .credentials(minioProps.getAccessKey(), minioProps.getSecretKey())
                .build();
    }
}