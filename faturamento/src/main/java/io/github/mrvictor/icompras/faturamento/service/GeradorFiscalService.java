package io.github.mrvictor.icompras.faturamento.service;

import io.github.mrvictor.icompras.faturamento.bucket.BucketFile;
import io.github.mrvictor.icompras.faturamento.bucket.BucketService;
import io.github.mrvictor.icompras.faturamento.model.Pedido;
import io.github.mrvictor.icompras.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeradorFiscalService {

    // Para utilizar algo de resources
    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscalResource;
    @Value("classpath:reports/logo.png")
    private Resource logo;
    private final BucketService bucketService;
    private final FaturamentoPublisher faturamentoPublisher;

    public void gerar(Pedido pedido) {
        log.info("Gerada nota fiscal para o pedido {}", pedido.codigo());

        try {
            byte[] byteArray = gerarNota(pedido);
            String nomeArquivo = String.format("notafiscal_pedido_%d.pdf", pedido.codigo());
            BucketFile file = new BucketFile(
                nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length
            );

            bucketService.upload(file);

            String urlArquivo = bucketService.getURL(nomeArquivo);

            faturamentoPublisher.publicar(pedido, urlArquivo);

            log.info("Gerada nota fiscal, nota do arquivo {}", nomeArquivo);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private byte[] gerarNota(Pedido pedido) {
        try(InputStream inputStream = notaFiscalResource.getInputStream()) {
            Map<String, Object> params = createParams(pedido);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pedido.itens());
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch(Exception e) {
            throw new RuntimeException("Erro ao gerar nota fiscal", e);
        }
    }

    private Map<String, Object> createParams(Pedido pedido) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put("NOME", pedido.cliente().nome());
        params.put("CPF", pedido.cliente().cpf());
        params.put("LOGRADOURO", pedido.cliente().logradouro());
        params.put("NUMERO", pedido.cliente().numero());
        params.put("EMAIL", pedido.cliente().email());
        params.put("TELEFONE", pedido.cliente().telefone());
        params.put("BAIRRO", pedido.cliente().bairro());

        params.put("DATA_PEDIDO", pedido.data());
        params.put("TOTAL_PEDIDO", pedido.total());

        params.put("LOGO", logo.getFile().getAbsolutePath());

        return params;
    }
}