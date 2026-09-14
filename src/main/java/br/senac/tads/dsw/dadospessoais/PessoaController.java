package br.senac.tads.dsw.dadospessoais;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
	public List<Pessoa> obterPessoas(){
		return  pessoaService.obterPessoas();
	}

	@GetMapping("/{username}")
	public Pessoa obterPessoa(@PathVariable("username") String username){
		Optional<Pessoa> optPessoa = pessoaService.obterPessoa(username);
		if (optPessoa.isEmpty()){
			throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return optPessoa.get();
	}
}
