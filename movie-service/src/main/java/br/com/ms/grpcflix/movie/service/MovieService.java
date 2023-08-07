package br.com.ms.grpcflix.movie.service;

import br.com.ms.grpc.movie.MovieDTO;
import br.com.ms.grpc.movie.MovieSearchRequest;
import br.com.ms.grpc.movie.MovieSearchResponse;
import br.com.ms.grpc.movie.MovieServiceGrpc;
import br.com.ms.grpcflix.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDTO> movieDTOList = this.movieRepository.getMovieByGenreOrderByMovieYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDTO.newBuilder()
                        .setTitle(movie.getTitle())
                        .setMovieYear(movie.getMovieYear())
                        .setRating(movie.getRating())
                        .build())
                .collect(Collectors.toList());
        MovieSearchResponse movieSearchResponse = MovieSearchResponse.newBuilder().addAllMovie(movieDTOList).build();
        responseObserver.onNext(movieSearchResponse);
        responseObserver.onCompleted();
    }
}
