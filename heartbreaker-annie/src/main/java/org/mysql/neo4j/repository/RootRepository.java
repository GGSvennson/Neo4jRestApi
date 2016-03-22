package org.mysql.neo4j.repository;

import org.mysql.neo4j.domain.RootNeo;
import org.springframework.data.neo4j.repository.GraphRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepository extends GraphRepository<RootNeo> {

}
