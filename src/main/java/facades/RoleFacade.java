package facades;

import entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RoleFacade {

    private static EntityManagerFactory emf;
    private static RoleFacade instance;

    public RoleFacade() {
    }

    public static RoleFacade getRoleFacade(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new RoleFacade();
        }
        return instance;
    }

}
