
# Compila o MinIO a partir do código-fonte
FROM golang:1.24-bookworm AS builder

ENV CGO_ENABLED=0

RUN go install github.com/minio/minio@latest

# Imagem final
FROM debian:bookworm-slim

RUN apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder /go/bin/minio /usr/local/bin/minio

EXPOSE 9000 9001

ENTRYPOINT ["minio"]