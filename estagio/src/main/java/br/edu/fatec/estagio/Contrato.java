package br.edu.fatec.estagio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private String empresa;
    private LocalDate inicioContrato;
    private LocalDate terminoContrato;
    private int cargaHoraria;
    private String area;
    private String funcao;
    private Supervisor supervisor;

    
}
