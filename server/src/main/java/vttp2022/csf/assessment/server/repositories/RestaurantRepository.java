package vttp2022.csf.assessment.server.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {



	@Autowired
    private MongoTemplate mongoTemplate;


	private static final String Comment_COL = "comment";
	private static final String field = "resturantid";


	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	


    //databasename.collectionname.distinct("name of field")

	//local.Restaurants.distinct("cuisines")
	public List<Restaurant> getCuisines(int limit, int skip) {

		Query query = (new Query()).limit(limit).skip(skip);
        return mongoTemplate.find(query, Document.class, "cuisines")
            .stream()
            .map(v -> { return Restaurant.create(v); })
            .toList();
    }
	
		

	

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	// local.restuarants.find({ cusine:"hamburger" })
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
		
		Criteria criteria = Criteria.where("cuisine").is(cuisine);
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, Document.class, "name")
            .stream()
            .map(v -> {
                return Restaurant.create(v);
            })
            .toList();

		// Implmementation in here

	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  local.restuarants.find({ restaurant_id:"40827287" })
	public Optional<Restaurant> getRestaurant(String restaurantId) {
	
	
		MatchOperation name = Aggregation.match(
			Criteria.where("restaurantId").is(Integer.parseInt(restaurantId)));

	LookupOperation link = Aggregation.lookup(field,
			"restaurantId", "name", "address");

	ProjectionOperation projection = Aggregation
			.project("restaurantId", "name", "address", "imgurl")
			.and("resturant._id").as("restuarant");


	Aggregation pipeline = Aggregation
			.newAggregation(name, link, projection);
	AggregationResults<Document> results = mongoTemplate
			.aggregate(pipeline, "restaurants", Document.class);
	if (!results.iterator().hasNext())
		return Optional.empty();

	Document doc = results.iterator().next();
	Restaurant r = Restaurant.create(doc);
	return Optional.of(r);		
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {


		mongoTemplate.insert(comment, Comment_COL);

		return ;
		
		// Implmementation in here
		
	}
	
	// You may add other methods to this class

}
