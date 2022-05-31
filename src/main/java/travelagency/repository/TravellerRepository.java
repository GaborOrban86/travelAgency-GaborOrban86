package travelagency.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TravellerRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
