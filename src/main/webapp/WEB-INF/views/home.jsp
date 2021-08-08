<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>

<!-- Referencia para arquivos CSS -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />

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

	<div class="container mt-4">	
		<div id="grafico"></div>		
	</div>

	<!-- Referencia para arquivos JS -->
	<script src="resources/js/bootstrap.min.js"></script>
	
	<!-- Referencia do JQuery -->
	<script src="resources/js/jquery-3.6.0.min.js"></script>	

	<!-- Referencia dos arquivos do Highcharts -->
	<script src="resources/js/highcharts.js"></script>
	<script src="resources/js/highcharts-3d.js"></script>
	<script src="resources/js/exporting.js"></script>
	<script src="resources/js/export-data.js"></script>
	
	<script>
		$(document).ready(function(){
			
			var dados = [
				{ data : [${qtd_admitido}], name : 'Funcionários admitidos' },
				{ data : [${qtd_afastado}], name : 'Funcionários afastados' },
				{ data : [${qtd_ferias}], name : 'Funcionários de férias' },
				{ data : [${qtd_demitido}], name : 'Funcionários demitidos' }
			];
			
			var array = [];
			for(var i = 0; i < dados.length; i++){
				array.push([ dados[i].name, dados[i].data[0] ]);
			}
			
			new Highcharts.Chart({
				chart : {
					type : 'pie',
					renderTo : 'grafico'
				},
				title : {
					text : 'Gráfico de Funcionários por Situação.'
				},
				subtitle : {
					text : 'Total de funcionários por situação cadastrada.'
				},
				exporting : { enabled : false },
				credits : { enabled : false },
				plotOptions : {
					pie : {
						innerSize: '60%'
					}
				},
				series : [
					{ data : array }
				]
			})
		})
	</script>

</body>
</html>





