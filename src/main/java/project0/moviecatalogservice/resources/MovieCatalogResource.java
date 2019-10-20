package project0.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import project0.moviecatalogservice.models.CatalogItem;
import project0.moviecatalogservice.models.Movies;
import project0.moviecatalogservice.models.Ratings;
import project0.moviecatalogservice.models.UserRating;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRatings().stream().map(rating -> {
            Movies movies = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),Movies.class);
            return new CatalogItem(movies.getName(),"test desc",rating.getRating());

        }).collect(Collectors.toList());

    }
}
