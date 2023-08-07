package br.com.ms.grpcflix.movie.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString
@Table(name = "movie")
public class MovieEntity {
    @Id
    private int id;
    private String title;
    private int movieYear;
    private double rating;
    private String genre;
}
