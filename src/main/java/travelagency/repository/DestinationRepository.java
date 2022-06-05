package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Destination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class DestinationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Destination findByName(String name) {
        return entityManager.createQuery(
                "SELECT d FROM Destination d WHERE d.name = '" + name + "'", Destination.class).getSingleResult();
    }
}
