package br.senac.tads.dsw.dadospessoais.seguranca;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		try {
			// Delega a autenticação ao Spring Security.
			// Internamente: chama loadUserByUsername() e compara a senha com o encoder.
			// Se as credenciais estiverem erradas, lança BadCredentialsException.
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginDto.getUsername(), loginDto.getSenha()
				)
			);

			// Autenticação bem-sucedida.
			// Por enquanto retornamos um token fixo sem significado real.
			// Este valor será substituído por um JWT real nos passos 6.6 a 6.8.
			return ResponseEntity.ok(Map.of("token", "token-fixo-provisorio-sem-significado"));

		} catch (BadCredentialsException e) {
			// Mensagem genérica — não revela se o problema é o username ou a senha
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("erro", "Usuário ou senha inválidos"));
		}
	}
}
