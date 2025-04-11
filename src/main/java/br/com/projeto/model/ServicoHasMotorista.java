package br.com.projeto.model;

public class ServicoHasMotorista {
    private Servico servico;
    private Motorista motorista;
    private Caminhao caminhao;

    public ServicoHasMotorista() {}

    public ServicoHasMotorista(Servico servico, Motorista motorista, Caminhao caminhao) {
        this.servico = servico;
        this.motorista = motorista;
        this.caminhao = caminhao;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }
}
