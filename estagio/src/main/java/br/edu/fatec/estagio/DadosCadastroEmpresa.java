package br.edu.fatec.estagio;

import java.util.List;

public record DadosCadastroEmpresa(
		
		@NotBlank
		Long id; 
		String nomeEmpresa;
		String areaAtuacao;
		String cidade;
		String cep;
		String email;
		String site;
		String telefone;
		String nomeContato;
		String cargoContato;
		String areaDepartamento;
		String Linkedin;
	) {

}
