package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Destination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DestinationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Destination findById(Integer id) {
        return entityManager.find(Destination.class, id);
    }
}

