package com.spring.dao;

import com.spring.model.User;
 

public interface UserDao {  
  
    User findByName(String name);  
  
    User findByLoginName(String loginName);  
      
    void saveOrUpdate(User user);  
}  