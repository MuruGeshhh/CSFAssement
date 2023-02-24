package vttp2022.csf.assessment.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping(path="/api")

public class ResturantController {

    @Autowired
    private RestaurantService resSvc;


    @GetMapping(path="/cuisines", consumes=MediaType.APPLICATION_JSON_VALUE , produces =MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity<String> getCuisines(@RequestParam(defaultValue="20") int limit, @RequestParam(defaultValue="0") int offset) {

        List<Restaurant> res = resSvc.getCuisines(limit, offset);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        res.stream()
            .forEach(v -> {
                arrBuilder.add(v.toJson());
            });

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/{cuisines}/restaurants", consumes=MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCuisinesbyRestaurants(@PathVariable String cuisines) {

        List<Restaurant> comments = resSvc.getRestaurantsByCuisine(cuisines);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        comments.stream()
            .forEach(v -> {
                arrBuilder.add(v.toJson());
            });

        return ResponseEntity.ok(arrBuilder.build().toString());
    }





    
}
