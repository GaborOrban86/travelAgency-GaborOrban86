package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Travel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TravelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Travel save(Travel toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Travel findById(Integer id) {
        return entityManager.find(Travel.class, id);
    }

    public List<Travel> findAll() {
        return entityManager.createQuery("SELECT t FROM Travel t",
                Travel.class).getResultList();
    }

    public void delete(Travel toDelete) {
        toDelete.setDeleted(true);
    }
}
