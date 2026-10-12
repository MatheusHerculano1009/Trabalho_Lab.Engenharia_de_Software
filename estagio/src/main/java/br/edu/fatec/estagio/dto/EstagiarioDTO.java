package br.edu.fatec.estagio.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class EstagiarioDTO {

    private Long id;
    
    @NotBlank(message = "Nome não pode estar vazio!")
    private String nome;

    @NotBlank(message = "RA não pode estar vazio!")
    @Size(min = 13, max = 13, message = "Numeração inválida para RA!")
	private String ra;

    @Size(min = 8, max = 8, message = "Numeração inválida para telefone!")
    private String telefone;
    private String email;
    private String escolaOrigem;
    @DecimalMin(value = "0.0", message = "A faixa de renda deve ser maior ou igual a 0!")
    private double faixaRenda;
    private String etnia;
    private String genero;
    private String curso;
    private String semestre;

    @Min(value = 1, message = "O período mínimo é 1!")
    @Max(value = 10, message = "O período máximo é 10!")
    private int periodo;

}
