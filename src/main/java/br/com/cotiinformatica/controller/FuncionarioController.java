package br.com.cotiinformatica.controller;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dto.FuncionarioCadastroDTO;
import br.com.cotiinformatica.dto.FuncionarioConsultaDTO;
import br.com.cotiinformatica.dto.FuncionarioEdicaoDTO;
import br.com.cotiinformatica.entities.Funcionario;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.enums.SituacaoFuncionario;
import br.com.cotiinformatica.helpers.DateHelper;
import br.com.cotiinformatica.reports.FuncionarioReport;
import br.com.cotiinformatica.repositories.FuncionarioRepository;

/*
 * Classe controladora do Spring MVC
 * Sua principal função é gerenciar páginas JSP do projeto
 */
@Controller
public class FuncionarioController {
	
	@Autowired //inicializar automaticamente!
	private FuncionarioRepository funcionarioRepository;

	@RequestMapping("/funcionario-cadastro")
	public ModelAndView cadastro() {

		// instanciando o objeto ModelAndView e definindo o nome da página
		// que será aberta por este método..
		ModelAndView modelAndView = new ModelAndView("funcionario-cadastro");
		
		//gerando o conteudo para o campo de seleção de situação de funcionario..
		modelAndView.addObject("situacoes", SituacaoFuncionario.values());

		// enviando para a página o DTO que fará a coleta dos dados
		modelAndView.addObject("dto", new FuncionarioCadastroDTO());

		return modelAndView; // segue para a página
	}

	@RequestMapping(value = "cadastrarFuncionario", method = RequestMethod.POST)
	public ModelAndView cadastrarFuncionario(FuncionarioCadastroDTO dto) {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-cadastro");
		
		try {
			
			String erros = "";
			
			//verificar se o cpf informado já encontra-se cadastrado na base de dados..
			if(funcionarioRepository.findByCpf(dto.getCpf()) != null) {
				erros += "O CPF informado já encontra-se cadastrado. ";
			}
			//verificar se a matricula informada já encontra-se cadastrado na base de dados..
			if(funcionarioRepository.findByMatricula(dto.getMatricula()) != null) {
				erros += "A matrícula informada já encontra-se cadastrada. ";
			}
			
			if(erros != "")
				throw new Exception(erros);
				
			Funcionario funcionario = new Funcionario();
			
			funcionario.setNome(dto.getNome());
			funcionario.setCpf(dto.getCpf());
			funcionario.setMatricula(dto.getMatricula());
			funcionario.setDataAdmissao(DateHelper.toDate(dto.getDataadmissao()));
			funcionario.setSituacao(SituacaoFuncionario.valueOf(dto.getSituacao()));
			
			funcionarioRepository.create(funcionario);
			
			modelAndView.addObject("mensagem_sucesso", "Funcionário cadastrado com sucesso");
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}		

		// enviando para a página o DTO que fará a coleta dos dados
		modelAndView.addObject("dto", new FuncionarioCadastroDTO());
		
		//gerando o conteudo para o campo de seleção de situação de funcionario..
		modelAndView.addObject("situacoes", SituacaoFuncionario.values());

		return modelAndView; // segue para a página
	}

	@RequestMapping("/funcionario-consulta")
	public ModelAndView consulta() {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-consulta");

		try {
			modelAndView.addObject("listagem_funcionarios", funcionarioRepository.findAll());			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		return modelAndView;
	}
	
	@RequestMapping(value = "consultarFuncionarios", method = RequestMethod.POST)
	public ModelAndView consultarFuncionarios(FuncionarioConsultaDTO dto) {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-consulta");

		try {
			
			//resgatar as datas de admissão..
			Date dataInicio = DateHelper.toDate(dto.getDataInicio());
			Date dataFim = DateHelper.toDate(dto.getDataFim());
			
			modelAndView.addObject("listagem_funcionarios", 
					funcionarioRepository.findByDataAdmissao(dataInicio, dataFim));			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		return modelAndView;		
	}	
	
	@RequestMapping("excluirFuncionario")
	public ModelAndView excluirFuncionario(Integer id) {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-consulta");

		try {
			
			//buscar o funcionario no banco de dados atraves do id..
			Funcionario funcionario = funcionarioRepository.findById(id);
			//excluir o funcionário..
			funcionarioRepository.delete(funcionario);
			
			modelAndView.addObject("mensagem_sucesso", "Funcionário " + funcionario.getNome() + ", excluído com sucesso.");			
			modelAndView.addObject("listagem_funcionarios", funcionarioRepository.findAll());			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		return modelAndView;		
		
	}	
	
	@RequestMapping("/funcionario-edicao")
	public ModelAndView funcionarioEdicao(Integer id) {
	
		ModelAndView modelAndView = new ModelAndView("funcionario-edicao");
		
		try {
			
			//buscar o funcionario no banco de dados atraves do id..
			Funcionario funcionario = funcionarioRepository.findById(id);
			
			//transferir os dados do funcionario para o DTO..
			FuncionarioEdicaoDTO dto = new FuncionarioEdicaoDTO();
			
			dto.setIdFuncionario(funcionario.getIdFuncionario());
			dto.setNome(funcionario.getNome());
			dto.setCpf(funcionario.getCpf());
			dto.setMatricula(funcionario.getMatricula());
			dto.setDataadmissao(DateHelper.toString(funcionario.getDataAdmissao()));
			dto.setSituacao(funcionario.getSituacao().toString());
			
			modelAndView.addObject("dto", dto);
			
			//gerando o conteudo para o campo de seleção de situação de funcionario..
			modelAndView.addObject("situacoes", SituacaoFuncionario.values());
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "atualizarFuncionario", method = RequestMethod.POST)
	public ModelAndView atualizarFuncionario(FuncionarioEdicaoDTO dto) {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-consulta");
		
		try {
			
			//buscar o funcionario no banco de dados atraves do id..
			Funcionario funcionario = funcionarioRepository.findById(dto.getIdFuncionario());
			
			//modificar os dados do funcionario..
			funcionario.setNome(dto.getNome());
			funcionario.setDataAdmissao(DateHelper.toDate(dto.getDataadmissao()));
			funcionario.setSituacao(SituacaoFuncionario.valueOf(dto.getSituacao()));
			
			//atualizando no banco de dados..
			funcionarioRepository.update(funcionario);
			
			modelAndView.addObject("mensagem_sucesso", "Funcionário " + funcionario.getNome() + " atualizado com sucesso");
			modelAndView.addObject("listagem_funcionarios", funcionarioRepository.findAll());
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		return modelAndView;
	}

	@RequestMapping("/funcionario-relatorio")
	public ModelAndView relatorio() {
		
		ModelAndView modelAndView = new ModelAndView("funcionario-relatorio");
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		
		return modelAndView;
	}
	
	@RequestMapping(value = "gerarRelatorioFuncionarios", method = RequestMethod.POST)
	public ModelAndView gerarRelatorioFuncionarios(FuncionarioConsultaDTO dto, 
			HttpServletRequest request, HttpServletResponse response) {
				
		ModelAndView modelAndView = new ModelAndView("funcionario-relatorio");
		modelAndView.addObject("dto", new FuncionarioConsultaDTO());
		
		try {
			
			//Capturando as datas enviados pelo formulário..
			Date dataInicio = DateHelper.toDate(dto.getDataInicio());
			Date dataFim = DateHelper.toDate(dto.getDataFim());
			
			//Obtendo o usuario autenticado no sistema..
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario_autenticado");
			
			//consulta de funcionários..
			List<Funcionario> funcionarios = funcionarioRepository.findByDataAdmissao(dataInicio, dataFim);
			
			//Gerando o relatorio..
			FuncionarioReport report = new FuncionarioReport();
			ByteArrayInputStream stream = report.generatePdfReport(dataInicio, dataFim, funcionarios, usuario);
						
			response.setContentType("application/pdf"); //tipo de arquivo..
			response.addHeader("Content-Disposition", "attachment; filename=funcionarios.pdf");
			
			//DOWNLOAD..
			byte[] dados = stream.readAllBytes(); //dados do arquivo
			//byte[] dados = IOUtils.toByteArray(stream);
			
			OutputStream out = response.getOutputStream();
			out.write(dados, 0, dados.length);
			out.flush();
			out.close();
			
			response.getOutputStream().flush();
			
			return null;
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		return modelAndView;
	}
}



