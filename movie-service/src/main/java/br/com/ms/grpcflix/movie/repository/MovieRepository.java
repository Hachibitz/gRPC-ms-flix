package br.com.ms.grpcflix.movie.repository;

import br.com.ms.grpcflix.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    List<MovieEntity> getMovieByGenreOrderByMovieYearDesc(String genre);
}
