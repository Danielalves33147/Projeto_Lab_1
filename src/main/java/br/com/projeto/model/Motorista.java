package br.com.projeto.model;

public class Motorista {
    private String cpf;  // Chave primária
    private String nomeMotorista;
    private String cnh;
    private String telefone;
    private String transportadorCpf;  // Nova variável para CPF do Transportador
 
    
    public Motorista() {}

    public Motorista(String nomeMotorista, String cpf, String cnh, String telefone) {
        this.nomeMotorista = nomeMotorista;
        this.cpf = cpf;
        this.cnh = cnh;
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	public String getTransportadorCpf() {
		return transportadorCpf;
	}

	public void setTransportadorCpf(String transportadorCpf) {
		this.transportadorCpf = transportadorCpf;
	}

}
