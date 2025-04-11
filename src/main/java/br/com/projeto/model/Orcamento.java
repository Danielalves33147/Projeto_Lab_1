package br.com.projeto.model;

import java.time.LocalDate;

public class Orcamento {
    private int idOrcamento;
    private double valorDiaria;
    private double valorTotal;
    private LocalDate dataEmissao;
    private int idServico;
    private String descricao;
    private String tipoServico;
    private String detalhes;

    public Orcamento() {}

    public Orcamento(int idOrcamento, double valorDiaria, double valorTotal,
                     LocalDate dataEmissao, int idServico, String descricao, String tipoServico,
                     String detalhes) {
        this.idOrcamento = idOrcamento;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
        this.dataEmissao = dataEmissao;
        this.idServico = idServico;
        this.descricao = descricao;
        this.tipoServico = tipoServico;
        this.detalhes = detalhes;
    }

    public int getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(int idOrcamento) {
        this.idOrcamento = idOrcamento;
    }


    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

}
