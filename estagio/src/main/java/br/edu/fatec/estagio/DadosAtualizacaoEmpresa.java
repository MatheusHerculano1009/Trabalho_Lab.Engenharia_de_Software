package br.edu.fatec.estagio;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoEmpresa(
	@NotNull
	Long id,
	String nome,
	String pais
	) {
}
