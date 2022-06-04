package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Accommodation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccommodationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Accommodation save(Accommodation toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Accommodation findById(Integer id) {
        return entityManager.find(Accommodation.class, id);
    }

    public List<Accommodation> findAll() {
        return entityManager.createQuery("SELECT a FROM Accommodation a",
                Accommodation.class).getResultList();
    }

    public Accommodation update(Accommodation toUpdate) {
        return entityManager.merge(toUpdate);
    }

    public void delete(Accommodation toDelete) {
        entityManager.remove(toDelete);
    }


}
