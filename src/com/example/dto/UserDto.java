package com.example.dto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entities.UserEntity;


public class UserDto {
	
	public void save(UserEntity user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("DACMS");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			
			transaction.begin();
			manager.persist(user);
			transaction.commit();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public UserEntity findByEmailId(String email) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("DACMS");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			
			transaction.begin();
			
			String jpql = "SELECT u FROM UserEntity u WHERE email=:userEmail";
			TypedQuery<UserEntity> query = manager.createQuery(jpql,UserEntity.class);
			query.setParameter("userEmail",email);
			UserEntity userResult = query.getSingleResult();
			
			transaction.commit();
			return userResult;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
}
