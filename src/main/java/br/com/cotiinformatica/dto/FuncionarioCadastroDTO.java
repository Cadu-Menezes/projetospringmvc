package br.com.cotiinformatica.dto;

public class FuncionarioCadastroDTO {

	private String nome;
	private String cpf;
	private String matricula;
	private String dataadmissao;
	private String situacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDataadmissao() {
		return dataadmissao;
	}

	public void setDataadmissao(String dataadmissao) {
		this.dataadmissao = dataadmissao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
