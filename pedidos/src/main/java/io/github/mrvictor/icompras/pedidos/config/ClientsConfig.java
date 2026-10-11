package io.github.mrvictor.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.github.mrvictor.icompras.pedidos.client")
public class ClientsConfig {

}