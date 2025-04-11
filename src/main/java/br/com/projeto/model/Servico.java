package br.com.projeto.model;

import java.time.LocalDate;

public class Servico {
    private int idServico;
    private String tipoServico;
    private String caminhaoUtilizado;
    private String motoristaCpf;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private double valorTotal;
    private String transportadorCpf;
    private String localOrigem;  
    private String localDestino; 
    private double distancia;    
    private LocalDate dataExecucao;  
    private String descricaoServico;
    private int idOrcamento;


    // Construtor sem parâmetros
    public Servico() {}

    // Construtor com parâmetros (sem o idServico, pois ele é auto-incrementado)
    public Servico(String tipoServico, String caminhaoUtilizado, String motoristaCpf, LocalDate dataInicio,
                   LocalDate dataTermino, double valorTotal, String transportadorCpf, String localOrigem,
                   String localDestino, double distancia, LocalDate dataExecucao) {
        this.tipoServico = tipoServico;
        this.caminhaoUtilizado = caminhaoUtilizado;
        this.motoristaCpf = motoristaCpf;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.valorTotal = valorTotal;
        this.transportadorCpf = transportadorCpf;
        this.localOrigem = localOrigem;
        this.localDestino = localDestino;
        this.distancia = distancia;
        this.dataExecucao = dataExecucao;
    }

    // Getters e Setters
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getCaminhaoUtilizado() {
        return caminhaoUtilizado;
    }

    public void setCaminhaoUtilizado(String caminhaoUtilizado) {
        this.caminhaoUtilizado = caminhaoUtilizado;
    }

    public String getMotoristaCpf() {
        return motoristaCpf;
    }

    public void setMotoristaCpf(String motoristaCpf) {
        this.motoristaCpf = motoristaCpf;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTransportadorCpf() {
        return transportadorCpf;
    }

    public void setTransportadorCpf(String transportadorCpf) {
        this.transportadorCpf = transportadorCpf;
    }

    // Métodos adicionados
    public String getLocalOrigem() {
        return localOrigem;
    }

    public void setLocalOrigem(String localOrigem) {
        this.localOrigem = localOrigem;
    }

    public String getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

	public String getDescricaoServico() {
		return descricaoServico;
	}

	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}

	public int getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(int idOrcamento) {
		this.idOrcamento = idOrcamento;
	}
}
