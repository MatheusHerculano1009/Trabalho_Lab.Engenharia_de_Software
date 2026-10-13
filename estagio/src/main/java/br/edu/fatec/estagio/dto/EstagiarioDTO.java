package br.edu.fatec.estagio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres!")
    private String nome;

    @NotBlank(message = "RA não pode estar vazio!")
    @Size(min = 13, max = 13, message = "Numeração inválida para RA!")
    private String ra;

    private String telefone;

    @NotBlank(message = "E-mail não pode estar vazio!")
    @Email(message = "E-mail inválido!")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres!")
    private String email;

    @NotBlank(message = "Curso não pode estar vazio!")
    @Size(max = 100, message = "Curso deve ter no máximo 100 caracteres!")
    private String curso;

    @Size(max = 20, message = "Semestre deve ter no máximo 20 caracteres!")
    private String semestre;

    @NotNull(message = "Informe o período!")
    @Min(value = 1, message = "O período mínimo é 1!")
    @Max(value = 10, message = "O período máximo é 10!")
    private Integer periodo;

    private String turno;

    @NotBlank(message = "Informe a escola de origem!")
    @Size(max = 150, message = "Escola deve ter no máximo 150 caracteres!")
    private String escolaOrigem;

    @NotBlank(message = "Informe o endereço da escola de origem!")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres!")
    private String enderecoEscola;

    @NotNull(message = "Informe quantas vezes prestou o vestibular!")
    @Min(value = 1, message = "O mínimo é 1 vez!")
    @Max(value = 20, message = "Valor inválido!")
    private Integer vezesVestibular;

    // É mesmo necessário, ter esses atributos?
    private String faixaRenda;
    private String etnia;
    private String genero;

}
