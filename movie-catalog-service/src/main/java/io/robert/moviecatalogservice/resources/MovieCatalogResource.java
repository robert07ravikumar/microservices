package io.robert.moviecatalogservice.resources;

import io.robert.moviecatalogservice.models.CatalogItem;
import io.robert.moviecatalogservice.models.Movie;
import io.robert.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

   /* @Autowired
    private WebClient.Builder weblClientBuilder;*/

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userid){

        UserRating userRatings = restTemplate.getForObject("http://rating-data-service/ratingdata/users/" + userid , UserRating.class);

        return userRatings.getUserRatings().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service:8081/movies/" + rating.getMovieId(),Movie.class);
            /*Movie movie = weblClientBuilder.build()
                        .get().uri("http://localhost:8081/movies/" + rating.getMovieId())
                        .retrieve().bodyToMono(Movie.class).block();*/

            return new CatalogItem(movie.getName(),"Desc",rating.getRating());
        }).collect(Collectors.toList());
    }
}