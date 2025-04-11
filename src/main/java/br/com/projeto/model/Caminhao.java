package br.com.projeto.model;

import java.time.LocalDate;

public class Caminhao {
    private String placa;
    private String modelo;
    private String tipo;
    private LocalDate anoFabricacao; // Mantém como LocalDate
    private double capacidadeMax;
    private String transportadorCpf;
    
    public Caminhao() {}
    
    // Construtor adequado
    public Caminhao(String placa, String modelo, String tipo, LocalDate anoFabricacao, double capacidadeMax) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anoFabricacao = anoFabricacao;
        this.capacidadeMax = capacidadeMax;
    }

    // Getters and setters para os campos
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(LocalDate anoFabricacao) { this.anoFabricacao = anoFabricacao; }

    public double getCapacidadeMax() { return capacidadeMax; }
    public void setCapacidadeMax(double capacidadeMax) { this.capacidadeMax = capacidadeMax; }

    public String getTransportadorCpf() { return transportadorCpf; }
    public void setTransportadorCpf(String transportadorCpf) { this.transportadorCpf = transportadorCpf; }
}
