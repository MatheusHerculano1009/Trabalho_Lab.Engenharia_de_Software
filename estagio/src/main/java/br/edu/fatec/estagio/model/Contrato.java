package br.edu.fatec.estagio.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Classe associativa: liga um Estagiario a uma Empresa por meio de um contrato de estágio.
@Entity
@Table(name = "contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    private LocalDate inicioContrato;
    private LocalDate terminoContrato;

    // Carga horária semanal, em horas
    private int cargaHoraria;

    private String area;
    private String cargo;
    private String funcao;

    // Presencial, Remoto ou Híbrido
    private String modalidade;

    // CLT ou PJ
    private String tipoContrato;

    private String supervisor;
    private String cargoSupervisor;

    // Duração calculada a partir das datas (não é gravada no banco)
    @Transient
    public long getDuracaoMeses() {
        if (inicioContrato == null || terminoContrato == null) {
            return 0;
        }
        return ChronoUnit.MONTHS.between(inicioContrato, terminoContrato.plusDays(1));
    }

}
