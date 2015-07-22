package com.spring.dao.impl;
import javax.persistence.EntityManager;  
import javax.persistence.PersistenceContext;  
  


import org.springframework.stereotype.Repository;  

import com.spring.dao.UserDao;
import com.spring.model.User;
   
  
@Repository  
public class UserDaoImpl implements UserDao {  
      
    @PersistenceContext  
    private EntityManager em;  
  
    @Override  
    public User findByName(String name) {  
        return null;  
    }  
  
    @Override  
    public User findByLoginName(String loginName) {  
        return null;  
    }  
  
    @Override  
    public void saveOrUpdate(User user) {  
        em.persist(user);  
    }  
  
}  
