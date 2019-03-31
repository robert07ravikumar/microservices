package io.robert.ratingsdataservice.resources;

import io.robert.ratingsdataservice.models.Rating;
import io.robert.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {


    @RequestMapping("/{movieid}")
    public Rating getRating(@PathVariable("movieid") String movieId){
        return new Rating(movieId,4);
    }

    @RequestMapping("users/{userId}")
    public List<Rating> getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("1",4),
                new Rating("2",4)
        );

        UserRating userRating =  new UserRating();
        userRating.setUserRatings(ratings);
        return userRating;
    }
}
