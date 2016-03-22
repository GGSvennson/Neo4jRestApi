package org.mysql.neo4j.placesimport;

public class PlacesDbException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public PlacesDbException(String message) {
        super(message);
    }
    
    public PlacesDbException(String message, Throwable cause) {
        super(message, cause);
    }

}
