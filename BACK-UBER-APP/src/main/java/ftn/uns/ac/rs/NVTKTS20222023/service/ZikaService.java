package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.model.Zika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ZikaService {

//    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void save() {

//        entityManager.getTransaction().begin();

        // Example of saving an entity to the database
        Zika entity = new Zika();
        entity.setMessage("zika se upisao");
        entityManager.persist(entity);

//        entityManager.getTransaction().commit();

    }
}
