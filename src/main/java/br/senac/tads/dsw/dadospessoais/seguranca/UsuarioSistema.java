package br.senac.tads.dsw.dadospessoais.seguranca;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


// Representa um usuário no contexto do Spring Security.
// UserDetails é a interface padrão que o framework usa para carregar e inspecionar usuários.
// Ao implementa-lá
public class UsuarioSistema implements UserDetails {

	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public UsuarioSistema(String username, String password, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	// getAuthorities() retorna lista de permissões (roles) do usuário.
	// GrantedAuthority é uma interface; SimpleGrantedAuthority é a implementação padrão
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){ return authorities;}

	// Os quatro métodos abaixo controlam o estado da conta.
	// Retornam true por simplicidade - em produção você implementaria lógica de bloqueio,
	// expiração de senha etc.., consultando um banco de dados.

	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }

}
