package br.edu.fatec.estagio.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {

    private Long id;

    @NotNull(message = "Selecione o estagiário!")
    private Long estagiarioId;

    @NotNull(message = "Selecione a empresa!")
    private Long empresaId;

    @NotNull(message = "Informe a data de início!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate inicioContrato;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate terminoContrato;

    @NotNull(message = "Informe a carga horária semanal!")
    //@Min(value = 1, message = "A carga horária mínima é 1 hora!")
    //@Max(value = 30, message = "A carga horária máxima é 30 horas semanais!")
    private Integer cargaHoraria;

    @NotBlank(message = "Informe a área!")
    //@Size(max = 100, message = "Área deve ter no máximo 100 caracteres!")
    private String area;

    @NotBlank(message = "Informe o cargo!")
    //@Size(max = 100, message = "Cargo deve ter no máximo 100 caracteres!")
    private String cargo;

    @NotBlank(message = "Informe a função!")
    //@Size(max = 255, message = "Função deve ter no máximo 255 caracteres!")
    private String funcao;

    @NotBlank(message = "Selecione a modalidade do estágio!")
    private String modalidade;

    @NotBlank(message = "Selecione o supervisor!")
    //@Size(max = 150, message = "Supervisor deve ter no máximo 150 caracteres!")
    private String supervisor;

    @NotBlank(message = "Selecione o cargo do supervisor!")
    //@Size(max = 100, message = "Cargo do supervisor deve ter no máximo 100 caracteres!")
    private String cargoSupervisor;

}
