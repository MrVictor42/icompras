package io.github.mrvictor.icompras.pedidos.client;

import io.github.mrvictor.icompras.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", path = "/clientes")
public interface ClientesClient {

    @GetMapping("{codigo}")
    ResponseEntity<ClienteRepresentation> obterDados(@PathVariable Long codigo);
}