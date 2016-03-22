package org.mysql.neo4j.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.data.neo4j.annotation.*;

@NodeEntity
public class Place {

	@GraphId
	protected Long nodeId;
	
    @Indexed(unique=true)
    private String id;
    
    @Indexed
    private String name;
    
    @Labels 
    private Collection<String> labels = Collections.emptySet(); 

    
    protected Place(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Place() {
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addLabel(String label) {
        HashSet<String> newLabels = new HashSet<>(this.labels);
        if (newLabels.add(label)) this.labels = newLabels;
    }

    public void removeLabel(String label) {
        HashSet<String> newLabels = new HashSet<>(this.labels);
        if (newLabels.remove(label)) this.labels = newLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(place.nodeId);

    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, id);
    }

}
