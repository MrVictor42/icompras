package io.github.mrvictor.icompras.faturamento.api;

import io.github.mrvictor.icompras.faturamento.bucket.BucketFile;
import io.github.mrvictor.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        try(InputStream inputStream = file.getInputStream()) {
            MediaType type = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
            BucketFile bucketFile = new BucketFile(file.getOriginalFilename(), inputStream, type, file.getSize());

            bucketService.upload(bucketFile);

            return ResponseEntity.status(HttpStatus.CREATED).body("Arquivo Criado Com Sucesso");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}