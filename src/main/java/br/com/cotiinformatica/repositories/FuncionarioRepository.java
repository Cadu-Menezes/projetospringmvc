package br.com.cotiinformatica.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.cotiinformatica.entities.Funcionario;
import br.com.cotiinformatica.enums.SituacaoFuncionario;
import br.com.cotiinformatica.helpers.DateHelper;
import br.com.cotiinformatica.interfaces.IFuncionarioRepository;

public class FuncionarioRepository implements IFuncionarioRepository {

	// atributo
	private JdbcTemplate jdbcTemplate;

	// para que a configuração do DataSource possa ser passada para o objeto
	// jdbcTemplate
	// é necessario criarmos um método construtor que receba um data source como
	// parametro
	public FuncionarioRepository(DataSource dataSource) {
		// inicializando o jdbcTemplate com o dataSource
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Funcionario entity) throws Exception {

		String sql = "insert into funcionario(nome, cpf, matricula, dataadmissao, situacao) values(?,?,?,?,?)";

		Object[] params = { 
				entity.getNome(), 
				entity.getCpf(), 
				entity.getMatricula(), 
				DateHelper.toString(entity.getDataAdmissao()),
				entity.getSituacao().toString() 
			};

		jdbcTemplate.update(sql, params);
	}

	@Override
	public void update(Funcionario entity) throws Exception {

		String sql = "update funcionario set nome = ?, cpf = ?, matricula = ?, dataadmissao = ?, situacao = ? "
				+ "where idfuncionario = ?";

		Object[] params = { 
				entity.getNome(), 
				entity.getCpf(), 
				entity.getMatricula(), 
				DateHelper.toString(entity.getDataAdmissao()),
				entity.getSituacao().toString(), 
				entity.getIdFuncionario() 
			};

		jdbcTemplate.update(sql, params);

	}

	@Override
	public void delete(Funcionario entity) throws Exception {

		String sql = "delete from funcionario where idfuncionario = ?";

		Object[] params = { entity.getIdFuncionario() };

		jdbcTemplate.update(sql, params);

	}

	@Override
	public List<Funcionario> findAll() throws Exception {

		String sql = "select * from funcionario order by nome";

		List<Funcionario> lista = jdbcTemplate.query(sql, new RowMapper<Funcionario>() {

			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getFuncionario(rs);
			}

		});

		return lista;
	}

	@Override
	public Funcionario findById(Integer id) throws Exception {

		String sql = "select * from funcionario where idfuncionario = ?";

		Object[] params = { id };

		List<Funcionario> lista = jdbcTemplate.query(sql, params, new RowMapper<Funcionario>() {

			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getFuncionario(rs);
			}

		});

		if (lista.size() == 1) { // verificando se o funcionario foi encontrado..
			return lista.get(0); // retornando o primeiro resultado obtido..
		}

		return null;
	}

	@Override
	public Funcionario findByCpf(String cpf) throws Exception {

		String sql = "select * from funcionario where cpf = ?";

		Object[] params = { cpf };

		List<Funcionario> lista = jdbcTemplate.query(sql, params, new RowMapper<Funcionario>() {

			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getFuncionario(rs);
			}

		});

		if (lista.size() == 1) {
			return lista.get(0);
		}

		return null;
	}

	@Override
	public Funcionario findByMatricula(String matricula) throws Exception {

		String sql = "select * from funcionario where matricula = ?";

		Object[] params = { matricula };

		List<Funcionario> lista = jdbcTemplate.query(sql, params, new RowMapper<Funcionario>() {

			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getFuncionario(rs);
			}

		});

		if (lista.size() == 1) {
			return lista.get(0);
		}

		return null;
	}

	@Override
	public List<Funcionario> findByDataAdmissao(Date dataInicio, Date dataFim) throws Exception {

		String sql = "select * from funcionario where dataadmissao between ? and ? order by dataadmissao";

		Object[] params = { DateHelper.toString(dataInicio), DateHelper.toString(dataFim) };

		List<Funcionario> lista = jdbcTemplate.query(sql, params, new RowMapper<Funcionario>() {

			@Override
			public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getFuncionario(rs);
			}

		});

		return lista;
	}
	
	@Override
	public Integer countBySituacao(SituacaoFuncionario situacao) throws Exception {

		String sql = "select count(*) from funcionario where situacao = ?";

		Object[] params = { situacao.toString() };
				
		//queryForObject -> utilizado para consultas que retornam apenas 1 campo
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	// método privado para fazer a leitura dos dados do funcionario
	// em cada método de consulta do repositorio (RowMapper)
	private Funcionario getFuncionario(ResultSet rs) throws SQLException {

		Funcionario funcionario = new Funcionario();

		funcionario.setIdFuncionario(rs.getInt("idfuncionario"));
		funcionario.setNome(rs.getString("nome"));
		funcionario.setCpf(rs.getString("cpf"));
		funcionario.setMatricula(rs.getString("matricula"));
		funcionario.setDataAdmissao(rs.getDate("dataadmissao"));
		funcionario.setSituacao(SituacaoFuncionario.valueOf(rs.getString("situacao")));

		return funcionario;
	}	

}
