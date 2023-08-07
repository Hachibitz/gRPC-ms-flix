package br.com.ms.grpcflix.aggregator.controller;

import br.com.ms.grpc.user.UserResponse;
import br.com.ms.grpcflix.aggregator.dto.SuggestedMovie;
import br.com.ms.grpcflix.aggregator.dto.UserGenre;
import br.com.ms.grpcflix.aggregator.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private AggregatorService aggregatorService;

    @GetMapping("/user/{loginId}")
    public List<SuggestedMovie> getMovies(@PathVariable String loginId) {
        return this.aggregatorService.getUserMovieSuggestions(loginId);
    }

    @PutMapping("/user")
    public String setUserGenre(@RequestBody UserGenre userGenre) {
        return this.aggregatorService.setUserGenre(userGenre);
    }

}
