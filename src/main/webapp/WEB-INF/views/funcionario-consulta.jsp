<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>

<!-- Referencia para arquivos CSS -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/jquery.dataTables.min.css"/>

<!-- estilo CSS para o jquery validate -->
<style>	
	label.error { /* formatar as mensagens de erro do jquery validate */
		color: #d9534f;
	}
	select.error { /* formatar os campos com erro do jquery validate */
		border: 1px solid #d9534f;
	}
</style>

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Controle de Funcionários</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/projetoSpringMVC01/home">Página inicial</a></li>
					<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
							Gerenciar Funcionários 
					</a>
					<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="/projetoSpringMVC01/funcionario-cadastro">Cadastrar Funcionários</a></li>
							<li><a class="dropdown-item" href="/projetoSpringMVC01/funcionario-consulta">Consultar Funcionários</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="/projetoSpringMVC01/funcionario-relatorio">Relatório de Funcionários</a></li>
					</ul></li>
				</ul>
				<form class="d-flex">
					<span class="text-white mt-3" style="margin-right: 20px;">
						${usuario_autenticado.nome} (${usuario_autenticado.email})
					</span>
					<a href="/projetoSpringMVC01/logout" class="btn btn-outline-secondary mt-2"
						onclick="return confirm('Deseja realmente sair do sistema?')">
						Sair do Sistema
					</a>
				</form>
			</div>
		</div>
	</nav>
	
	<div class="nav-scroller bg-body shadow-sm">
		<nav class="nav nav-underline">
			<p class="mt-3 ml-4">
				&nbsp;&nbsp;&nbsp;Sistema desenvolvido em Spring MVC com Spring JDBC, Bootstrap e JQuery
			</p>
		</nav>
	</div>
	
	<c:if test="${not empty mensagem_sucesso}">
		<!-- mensagem de sucesso -->
		<div class="alert alert-success alert-dismissible fade show" role="alert">
  			<strong>Sucesso!</strong> ${mensagem_sucesso}
  			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
	
	<c:if test="${not empty mensagem_erro}">
		<!-- mensagem de erro -->
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
  			<strong>Erro!</strong> ${mensagem_erro}
  			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
		
	<div class="container mt-4">
		<h5>Consulta de funcionários</h5>
		<hr/>
		
		<form id="formconsulta" action="consultarFuncionarios" method="post">
		
			<label>Pesquisa de funcionários por período de admissão:</label>
		
			<div class="row">
			
				<div class="col-md-2">
					<form:input path="dto.dataInicio" id="dataInicio" name="dataInicio" 
						type="date" class="form-control"/>														
				</div>
				
				<div class="col-md-2">
					<form:input path="dto.dataFim" id="dataFim" name="dataFim" 
						type="date" class="form-control"/>														
				</div>
				
				<div class="col-md-4">
					<input type="submit" value="Realizar Pesquisa" class="btn btn-success"/>
				</div>
							
			</div>	
		
		</form>
		
		<table id="tabelafuncionarios" class="table table-striped">
			<thead>
				<tr>
					<th>Nome do Funcionário</th>
					<th>CPF</th>
					<th>Matrícula</th>
					<th>Data de Admissão</th>
					<th>Situação</th>
					<th>Operações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listagem_funcionarios}" var="funcionario">
					<tr>
						<td>${funcionario.nome}</td>
						<td>${funcionario.cpf}</td>
						<td>${funcionario.matricula}</td>
						<td><fmt:formatDate pattern="EEE dd/MM/yyyy" value="${funcionario.dataAdmissao}"/></td>
						<td>${funcionario.situacao}</td>
						<td>
							<a href="/projetoSpringMVC01/funcionario-edicao?id=${funcionario.idFuncionario}" 
								class="btn btn-primary btn-sm">
								Editar
							</a>
							<a href="/projetoSpringMVC01/excluirFuncionario?id=${funcionario.idFuncionario}"
								onclick="return confirm('Deseja realmente excluir o(a) funcionario(a) ${funcionario.nome}?');" 
								class="btn btn-danger btn-sm">
								Excluir
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">Quantidade de funcionários: ${listagem_funcionarios.size()}</td>
				</tr>
			</tfoot>
		</table>
		
	</div>

	<!-- Referencia para arquivos JS -->
	<script src="resources/js/bootstrap.min.js"></script>
	
	<!-- Referencia para o JQuery -->
	<script src="resources/js/jquery-3.6.0.min.js"></script>
	
	<!-- Referencias para o JQuery validate -->
	<script src="resources/js/jquery.validate.min.js"></script>
	<script src="resources/js/messages_pt_BR.min.js"></script>
	
	<!-- Referencia para o JQuery datatable -->
	<script src="resources/js/jquery.dataTables.min.js"></script>

	<script>
		//quando a página for carregada, faça..
		$(document).ready(function(){ //page load, start..
						
			//aplicando validação ao formulário..
			$("#formconsulta").validate({
				//regras de validação..
				rules : {
					"dataInicio" : {
						required : true
					},
					"dataFim" : {
						required : true
					}
				}
			});
		
			//aplicando a formatação do datatable
			$("#tabelafuncionarios").DataTable({
		        language: {
		            url: '//cdn.datatables.net/plug-ins/1.10.24/i18n/Portuguese-Brasil.json'
		        }
		    });
			
		})
	</script>	

</body>
</html>