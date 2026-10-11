package br.edu.fatec.estagio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Contrato {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @ManyToOne 
    @JoinColumn(name="empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name="estagiario_id")
    private Estagiario estagiario;

    private LocalDate inicioContrato;
    private LocalDate terminoContrato;
    private int cargaHoraria;
    private String area;
    private String funcao;
    
}
