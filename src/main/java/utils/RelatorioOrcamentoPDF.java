package utils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import br.com.projeto.model.Orcamento;

public class RelatorioOrcamentoPDF {

    public static void gerarPDF(Orcamento orcamento, OutputStream out) throws Exception {
        Document documento = new Document();
        PdfWriter.getInstance(documento, out);
        documento.open();

        // Título
        Font tituloFonte = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Relatório de Orçamento", tituloFonte);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);

        documento.add(new Paragraph(" ")); // Espaço

        // Informações do orçamento
        PdfPTable tabela = new PdfPTable(2);
        tabela.setWidthPercentage(100);

        tabela.addCell("ID do Orçamento:");
        tabela.addCell(String.valueOf(orcamento.getIdOrcamento()));

        tabela.addCell("Data de Emissão:");
        tabela.addCell(orcamento.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        tabela.addCell("Descrição:");
        tabela.addCell(orcamento.getDescricao());

        tabela.addCell("Tipo de Serviço:");
        tabela.addCell(orcamento.getTipoServico());

        tabela.addCell("Valor da Diária:");
        tabela.addCell("R$ " + orcamento.getValorDiaria());

        tabela.addCell("Valor Total:");
        tabela.addCell("R$ " + orcamento.getValorTotal());

        tabela.addCell("Detalhes:");
        tabela.addCell(orcamento.getDetalhes());

        documento.add(tabela);
        documento.close();
    }
}
