package br.com.projeto.model;

public class ServicoHasMotorista {
    private int idServico;
    private String motoristaCpf;
    private String caminhaoPlaca;

    // Getters e Setters
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getMotoristaCpf() {
        return motoristaCpf;
    }

    public void setMotoristaCpf(String motoristaCpf) {
        this.motoristaCpf = motoristaCpf;
    }

    public String getCaminhaoPlaca() {
        return caminhaoPlaca;
    }

    public void setCaminhaoPlaca(String caminhaoPlaca) {
        this.caminhaoPlaca = caminhaoPlaca;
    }
}
