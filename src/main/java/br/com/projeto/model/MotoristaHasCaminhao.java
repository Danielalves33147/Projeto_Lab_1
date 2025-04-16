package br.com.projeto.model;

public class MotoristaHasCaminhao {
    private String motoristaCpf;
    private String caminhaoPlaca;

    // Getters e Setters
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
    
    public MotoristaHasCaminhao(String placaCaminhao, String cpfMotorista) {
        this.caminhaoPlaca = placaCaminhao;
        this.motoristaCpf = cpfMotorista;
    }

    public MotoristaHasCaminhao() {
        // Construtor vazio necessário para DAO e frameworks
    }

}
