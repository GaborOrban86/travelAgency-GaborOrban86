package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Accommodation;
import travelagency.domain.Traveller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TravellerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Traveller save(Traveller toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Traveller findById(Integer id) {
        return entityManager.find(Traveller.class, id);
    }

    public List<Traveller> findAll() {
        return entityManager.createQuery("SELECT t FROM Traveller t",
                Traveller.class).getResultList();
    }

    public Traveller update(Traveller toUpdate) {
        return entityManager.merge(toUpdate);
    }

    public void delete(Traveller toDelete) {
        entityManager.remove(toDelete);
    }
}
