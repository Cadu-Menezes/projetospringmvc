package br.com.cotiinformatica.entities;

import java.util.Date;

import br.com.cotiinformatica.enums.SituacaoFuncionario;

public class Funcionario {

	private Integer idFuncionario;
	private String nome;
	private String cpf;
	private String matricula;
	private Date dataAdmissao;
	private SituacaoFuncionario situacao;

	public Funcionario() {
		// TODO Auto-generated constructor stub
	}

	public Funcionario(Integer idFuncionario, String nome, String cpf, String matricula, Date dataAdmissao,
			SituacaoFuncionario situacao) {
		super();
		this.idFuncionario = idFuncionario;
		this.nome = nome;
		this.cpf = cpf;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.situacao = situacao;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

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

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public SituacaoFuncionario getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoFuncionario situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Funcionario [idFuncionario=" + idFuncionario + ", nome=" + nome + ", cpf=" + cpf + ", matricula="
				+ matricula + ", dataAdmissao=" + dataAdmissao + ", situacao=" + situacao + "]";
	}

}
