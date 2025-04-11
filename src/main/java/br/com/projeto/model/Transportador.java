package br.com.projeto.model;

import java.time.LocalDate;

public class Transportador {
    private String cpfCnpj;
    private String nomeTransportador;
    private String telefone;
    private String endereco;
    private LocalDate dataRegistro;

    // Construtor padrão
    public Transportador() {}

    // Construtor com todos os parâmetros
    public Transportador(String cpfCnpj, String nomeTransportador, String telefone, String endereco, LocalDate dataRegistro) {
        this.cpfCnpj = cpfCnpj;
        this.nomeTransportador = nomeTransportador;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataRegistro = dataRegistro;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomeTransportador() {
        return nomeTransportador;
    }

    public void setNomeTransportador(String nomeTransportador) {
        this.nomeTransportador = nomeTransportador;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
