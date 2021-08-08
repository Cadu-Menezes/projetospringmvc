package br.com.cotiinformatica.interfaces;

import br.com.cotiinformatica.entities.Usuario;

public interface IUsuarioRepository extends IBaseRepository<Usuario> {

	Usuario findByEmail(String email) throws Exception;

	Usuario findByEmailAndSenha(String email, String senha) throws Exception;
}
