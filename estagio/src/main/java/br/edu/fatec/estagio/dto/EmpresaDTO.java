package br.edu.fatec.estagio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {

    private Long id;

    @NotBlank(message = "Nome não pode estar vazio!")
    private String nomeEmpresa;

    @NotBlank(message = "CNPJ não pode estar vazio!")
    @Size(min = 14, max = 14, message = "Numeração inválida para CNPJ!")
	private String cnpj;

	private String areaAtuacao;
	private String cidade;
    @Size(min = 8, max = 8, message = "Numeração inválida para CEP!")
	private String cep;
	private String email;
	private String site;
    @Size(min = 8, max = 8, message = "Numeração inválida para telefone!")
	private String telefone;
	private String nomeContato;
	private String cargoContato;
	private String areaDepartamento;
	private String linkedin;
    
}
