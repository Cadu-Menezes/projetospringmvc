package br.com.cotiinformatica.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.interfaces.IUsuarioRepository;

public class UsuarioRepository implements IUsuarioRepository {

	//atributo
	private JdbcTemplate jdbcTemplate;
	
	//construtor..
	public UsuarioRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void create(Usuario entity) throws Exception {

		String sql = "insert into usuario(nome, email, senha, perfil) values(?, ?, md5(?), ?)";

		Object[] params = { 
				entity.getNome(),
				entity.getEmail(),
				entity.getSenha(),
				entity.getPerfil()
			};

		jdbcTemplate.update(sql, params);

	}

	@Override
	public void update(Usuario entity) throws Exception {

		String sql = "update Usuario set nome = ?, email = ?, senha = md5(?), perfil = ? where idusuario = ?";

		Object[] params = { 
				entity.getNome(),
				entity.getEmail(),
				entity.getSenha(),
				entity.getPerfil(),
				entity.getIdUsuario()
			};

		jdbcTemplate.update(sql, params);
	}

	@Override
	public void delete(Usuario entity) throws Exception {

		String sql = "delete from usuario where idusuario = ?";

		Object[] params = { 
				entity.getIdUsuario()
			};

		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<Usuario> findAll() throws Exception {

		String sql = "select * from usuario";
		
		List<Usuario> lista = jdbcTemplate.query(sql, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getUsuario(rs);
			}
		});
		
		return lista;
	}

	@Override
	public Usuario findById(Integer id) throws Exception {

		String sql = "select * from usuario where idusuario = ?";

		Object[] params = { id };

		List<Usuario> lista = jdbcTemplate.query(sql, params, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getUsuario(rs);
			}

		});

		if (lista.size() == 1) { 
			return lista.get(0); 
		}

		return null;
	}

	@Override
	public Usuario findByEmail(String email) throws Exception {

		String sql = "select * from usuario where email = ?";

		Object[] params = { email };

		List<Usuario> lista = jdbcTemplate.query(sql, params, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getUsuario(rs);
			}

		});

		if (lista.size() == 1) { 
			return lista.get(0); 
		}

		return null;
	}

	@Override
	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {

		String sql = "select * from usuario where email = ? and senha = md5(?)";

		Object[] params = { email, senha };

		List<Usuario> lista = jdbcTemplate.query(sql, params, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getUsuario(rs);
			}

		});

		if (lista.size() == 1) { 
			return lista.get(0); 
		}

		return null;
	}
	
	private Usuario getUsuario(ResultSet rs) throws SQLException {

		Usuario usuario = new Usuario();

		usuario.setIdUsuario(rs.getInt("idusuario"));
		usuario.setNome(rs.getString("nome"));
		usuario.setEmail(rs.getString("email"));
		usuario.setSenha(rs.getString("senha"));
		usuario.setPerfil(rs.getString("perfil"));

		return usuario;
	}

}
