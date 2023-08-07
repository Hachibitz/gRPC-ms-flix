package br.com.ms.grpcflix.aggregator.service;

import br.com.ms.grpc.common.Genre;
import br.com.ms.grpc.movie.MovieDTO;
import br.com.ms.grpc.movie.MovieSearchRequest;
import br.com.ms.grpc.movie.MovieSearchResponse;
import br.com.ms.grpc.movie.MovieServiceGrpc;
import br.com.ms.grpc.user.UserGenreUpdateRequest;
import br.com.ms.grpc.user.UserResponse;
import br.com.ms.grpc.user.UserSearchRequest;
import br.com.ms.grpc.user.UserServiceGrpc;
import br.com.ms.grpcflix.aggregator.dto.SuggestedMovie;
import br.com.ms.grpcflix.aggregator.dto.UserGenre;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregatorService {
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;

    public List<SuggestedMovie> getUserMovieSuggestions(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userServiceBlockingStub.getUserGenre(userSearchRequest);

        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieServiceBlockingStub.getMovies(movieSearchRequest);

        List<SuggestedMovie> movieDTOList = movieSearchResponse.getMovieList()
                .stream()
                .map(movieDTO ->
                    new SuggestedMovie(movieDTO.getTitle(), movieDTO.getMovieYear(), movieDTO.getRating())
                )
                .collect(Collectors.toList());

        return movieDTOList;
    }

    public UserResponse setUserGenre(UserGenre userGenre) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        UserResponse userResponse = this.userServiceBlockingStub.updateUserGenre(userGenreUpdateRequest);
        return userResponse;
    }
}
