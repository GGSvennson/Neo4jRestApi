package com.primefaces.hibernate.generic;

import java.io.Serializable;
 
public interface GenericDao<Entity, PK extends Serializable> {
    void create(Entity t);
    void update(Entity t);
    Entity find(PK id);
    void delete(Entity t);
}
