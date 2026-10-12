package br.edu.fatec.estagio.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "estagiario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Estagiario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private String nome;
    private String ra;
    private String telefone;
    private String email;
    private String escolaOrigem;
    private double faixaRenda;
    private String etnia;
    private String genero;
    private String curso;
    private String semestre;
    private int periodo;

	@OneToMany(mappedBy = "estagiario")
	private List<Contrato> contratos;

}