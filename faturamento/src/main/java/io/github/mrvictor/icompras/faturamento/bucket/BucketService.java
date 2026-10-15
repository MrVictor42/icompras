package io.github.mrvictor.icompras.faturamento.bucket;

import io.github.mrvictor.icompras.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file) {
        try {
            PutObjectArgs object = PutObjectArgs
                    .builder()
                    .bucket(props.getBucketName())
                    .object(file.name())
                    .stream(file.is(), file.size(), -1L)
                    .contentType(file.type().toString()) //application/pdf jpg/jpeg
                    .build();

            client.putObject(object);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getURL(String fileName) {
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs
                    .builder()
                    .method(Http.Method.GET)
                    .bucket(props.getBucketName())
                    .object(fileName)
                    .expiry(7, TimeUnit.DAYS)
                    .build();

            return client.getPresignedObjectUrl(args);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}