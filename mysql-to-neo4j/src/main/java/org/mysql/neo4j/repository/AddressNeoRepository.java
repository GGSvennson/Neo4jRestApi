package org.mysql.neo4j.repository;

import org.mysql.neo4j.domain.AddressNeo;
import org.springframework.data.neo4j.repository.GraphRepository; 

public interface AddressNeoRepository extends GraphRepository<AddressNeo> {
	
}
