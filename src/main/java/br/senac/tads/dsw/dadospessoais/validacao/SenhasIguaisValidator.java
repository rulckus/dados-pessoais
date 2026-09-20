package br.senac.tads.dsw.dadospessoais.validacao;

import br.senac.tads.dsw.dadospessoais.Pessoa;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SenhasIguaisValidator
		implements ConstraintValidator<SenhasIguais, Pessoa> {

	private String mensagem;

	@Override
	public void initialize(SenhasIguais annotation){
		this.mensagem = annotation.message();
	}

	@Override
	public boolean isValid(Pessoa pessoa, ConstraintValidatorContext context){
		boolean resultado = pessoa.getSenha() != null &&
			pessoa.getSenha().equals(pessoa.getSenhaRepeticao());
		if (!resultado){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(mensagem)
				.addPropertyNode("senha").addConstraintViolation();
		}
		return resultado;
	}
}
