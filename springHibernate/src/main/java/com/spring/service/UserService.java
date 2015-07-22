package com.spring.service;
import org.apache.log4j.Logger;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Component;  
import org.springframework.transaction.annotation.Transactional;  

import com.spring.dao.UserDao;
import com.spring.model.User;
  
  
@Component  
public class UserService {  
    private static Logger logger = Logger.getLogger(UserService.class);  
    @Autowired  
    private UserDao userDao;  
      
    @Transactional(readOnly=false)  
    public void saveUser(User user){  
        userDao.saveOrUpdate(user);  
        logger.debug(user.getUsername()+",±£´æ³É¹¦£¡");  
    }  
}  
