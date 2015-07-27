package com.spring.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.IUserDao;
import com.spring.model.User;
  
  
@Service 
public class UserService {  
    
    @Resource 
    private IUserDao userDao;  
      
    @Transactional(readOnly=false)  
    public void saveUser(User user){  
        userDao.saveOrUpdate(user);   
    }  
}  
