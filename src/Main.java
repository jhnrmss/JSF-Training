import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entities.UserEntity;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("DACMS");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trans = manager.getTransaction();
		try {
			
			trans.begin();
			String jpql = "SELECT u FROM UserEntity u WHERE email=:userEmail";
			TypedQuery<UserEntity> query = manager.createQuery(jpql,UserEntity.class);
			query.setParameter("userEmail", "test");
			UserEntity userResult = query.getSingleResult();
			trans.commit();
			
			System.out.println(userResult.getPassword());
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
