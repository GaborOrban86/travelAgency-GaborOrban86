package travelagency.repository;

import org.springframework.stereotype.Repository;
import travelagency.domain.Accommodation;
import travelagency.domain.Program;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProgramRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Program save(Program toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Program findById(Integer id) {
        return entityManager.find(Program.class, id);
    }

    public List<Program> findAll() {
        return entityManager.createQuery("SELECT p FROM Program p",
                Program.class).getResultList();
    }

    public void delete(Program toDelete) {
        toDelete.setDeleted(true);
    }
}
