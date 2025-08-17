package br.hendrew.goldenraspberryapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "\"year\"")
    @Max(value = 2050, message = "Ano máximo é 2050")
    @Min(value = 1900, message = "Ano mínimo é 1900")
    @NotNull(message = "Ano é obrigatório")
    private int year;

    @Column(name = "title")
    @NotNull(message = "Título é obrigatório")
    private String title;

    @Column(name = "studios")
    @NotNull(message = "Studios é obrigatório")
    private String studios;

    @Column(name = "producers")
    @NotNull(message = "Produção é obrigatório")
    private String producers;

    @Column(name = "winner")
    @NotNull(message = "Vencendor é obrigatório")
    private boolean winner;

}
