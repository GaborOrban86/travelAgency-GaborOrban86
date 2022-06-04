package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Accommodation;
import travelagency.domain.Travel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TravelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Travel save(Travel toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<Travel> findById(Integer id) {
        Travel travel = entityManager.find(Travel.class, id);
        return Optional.of(travel);
    }

    public List<Travel> findAll() {
        return entityManager.createQuery("SELECT t FROM Travel t",
                Travel.class).getResultList();
    }

    public void delete(Travel toDelete) {
        entityManager.remove(toDelete);
    }
}
