package org.neo4j.cineasts.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="RECOMMENDED")
public class Recommendation {

	@GraphId
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Movie movie;
    
    private String tagline;
    
    public Recommendation() {
    	
    }
    
    public Recommendation(User user, Movie movie, String tagline) {
    	this.user = user;
    	this.movie = movie;
    	this.tagline = tagline;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
    
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recommendation)) {
        	return false;
        }
        
        Recommendation recommendation = (Recommendation)o;
        
        if (tagline != null ? !tagline.equals(recommendation.tagline) : recommendation.tagline != null) {
            return false;
        }
        if (movie != null ? !movie.equals(recommendation.movie) : recommendation.movie != null) {
            return false;
        }
        if (user != null ? !user.equals(recommendation.user) : recommendation.user != null) {
            return false;
        }

        return true;
	}
	
	@Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        return result;
    }
}
