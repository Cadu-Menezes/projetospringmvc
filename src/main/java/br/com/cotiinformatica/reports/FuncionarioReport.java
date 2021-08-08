package br.com.cotiinformatica.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.cotiinformatica.entities.Funcionario;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.helpers.DateHelper;

public class FuncionarioReport {

	public ByteArrayInputStream generatePdfReport(Date dataInicio, Date dataFim, List<Funcionario> funcionarios,
			Usuario usuario) throws Exception {

		// criando um objeto que irá permitir fazermos o retorno do arquivo do relatorio
		// em formato byte (será utilizado posteriormente para download do arquivo)
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// desenhando o documento PDF com a biblioteca iText..
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);

		// abrir o documento PDF:
		document.open();

		//inserindo uma logo no relatorio
		Image logo = Image.getInstance(new URL("https://www.cotiinformatica.com.br/imagens/logo-coti-informatica.png"));
		document.add(logo);
		
		// escrevendo textos no relatório..
		document.add(new Paragraph("Relatório de Funcionários", fmtTitle()));
		
		document.add(new Paragraph("\n")); //quebra de linha
		
		document.add(new Paragraph("Data de geração: " + DateHelper.toStringPtBR(new Date()), fmtProfile()));

		document.add(new Paragraph("Período de admissão de funcionários de: " + DateHelper.toStringPtBR(dataInicio)
				+ " até: " + DateHelper.toStringPtBR(dataFim), fmtProfile()));

		document.add(new Paragraph("Nome do usuário: " + usuario.getNome(), fmtProfile()));
		document.add(new Paragraph("Email: " + usuario.getEmail(), fmtProfile()));
				
		document.add(new Paragraph("\n")); //quebra de linha

		// desenhando uma tabela para exibir os registros de funcionários..
		PdfPTable table = new PdfPTable(5); // 5 colunas!
		table.setWidthPercentage(100); //largura de 100%

		// cabeçalho da tabela
		table.addCell(new Paragraph("Nome do Funcionário", fmtHeaderCell()));
		table.addCell(new Paragraph("CPF", fmtHeaderCell()));
		table.addCell(new Paragraph("Matrícula", fmtHeaderCell()));
		table.addCell(new Paragraph("Data de Admissão", fmtHeaderCell()));
		table.addCell(new Paragraph("Situação", fmtHeaderCell()));

		// percorrer os funcionarios e exibir os registros:
		for (Funcionario funcionario : funcionarios) {

			table.addCell(new Paragraph(funcionario.getNome(), fmtDataCell()));
			table.addCell(new Paragraph(funcionario.getCpf(), fmtDataCell()));
			table.addCell(new Paragraph(funcionario.getMatricula(), fmtDataCell()));
			table.addCell(new Paragraph(DateHelper.toStringPtBR(funcionario.getDataAdmissao()), fmtDataCell()));
			table.addCell(new Paragraph(funcionario.getSituacao().toString(), fmtDataCell()));

		}

		// adicionar a tabela no documento pdf..
		document.add(table);
		
		document.add(new Paragraph("\n")); //quebra de linha
		
		document.add(new Paragraph("Quantidade de funcionários: " + funcionarios.size(), fmtProfile()));

		document.close(); // fechando o documento
		writer.close(); // fechando o buffer de memória

		// retornando o conteudo do relatorio em bytes
		return new ByteArrayInputStream(out.toByteArray());
	}

	//formatação do titulo do relatorio
	private Font fmtTitle() {

		Font font = new Font();

		font.setSize(20);
		font.setStyle("bold");
		font.setColor(15, 60, 120);

		return font;
	}
	
	//formatação dos dados do relatorio
	private Font fmtProfile() {

		Font font = new Font();
		font.setSize(12);

		return font;
	}
	
	//formatação do cabeçalho da tabela
	private Font fmtHeaderCell() {

		Font font = new Font();
		font.setSize(11);
		font.setStyle("bold");

		return font;
	}
	
	//formatação do conteudo da tabela
	private Font fmtDataCell() {

		Font font = new Font();
		font.setSize(11);

		return font;
	}

}
