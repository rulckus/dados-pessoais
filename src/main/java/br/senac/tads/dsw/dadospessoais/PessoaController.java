package br.senac.tads.dsw.dadospessoais;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService){
		this.pessoaService = pessoaService;
	}

	@GetMapping
	public List<PessoaDto> obterPessoas(){
		return  pessoaService.obterPessoas();
	}

	@GetMapping("/{username}")
	public PessoaDto obterPessoa(@PathVariable("username") String username){
		Optional<PessoaDto> optPessoa = pessoaService.obterPessoa(username);
		if (optPessoa.isEmpty()){
			throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return optPessoa.get();
	}

	@PostMapping("/sem-validacao")
	public ResponseEntity<?> incluiNovo(@RequestBody PessoaDto pessoa){
		pessoaService.incluirNovaPessoa(pessoa);
		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.path("/pessoas/{username}")
			.buildAndExpand(pessoa.getUsername())
			.toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping
	public ResponseEntity<?> incluiNovoComValidacao(@RequestBody @Valid PessoaDto pessoa){
		pessoaService.incluirNovaPessoa(pessoa);
		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.path("/pessoas/{username}")
			.buildAndExpand(pessoa.getUsername())
			.toUri();

		return ResponseEntity.created(location).build();
	}


	@PutMapping("/{username}")
	public ResponseEntity<?> atualizar(@PathVariable("username") String username,
									   @RequestBody @Valid PessoaAlteracaoDto pessoa){
		PessoaDto pessoaAlterada = pessoaService.alterarPessoa(username, pessoa);
		return ResponseEntity.ok().body(pessoaAlterada);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> remover(@PathVariable("username") String username){
		pessoaService.removerPessoa(username);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<ProblemDetail> tratarExcecao(NaoEncontradoException ex){
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
		return ResponseEntity.of(pd).build();
	}


}
