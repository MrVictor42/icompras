package io.github.mrvictor.icompras.faturamento.bucket;

import io.github.mrvictor.icompras.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient minioClient;
    private final MinioProps minioProps;

    public void upload(BucketFile file) {

    }

    public String getURL(String fileName) {

    }
}