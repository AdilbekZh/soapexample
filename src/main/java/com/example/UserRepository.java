package com.example;

import com.example.entity.UserModel;
import com.example.ws.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 14.06.2015.
 */
@Component
public class UserRepository {

    private final static Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static final List<User> userList = new ArrayList<User>();

    @Transactional
    public User addUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            UserModel userModel = new UserModel();
            userModel.setFname(user.getFname());
            userModel.setLname(user.getLname());

            session.save(userModel);
            transaction.commit();
            logger.info("User {} added", userModel);
            userList.add(user);
        } catch (Exception ex) {
            logger.error("Exception while trying to add User {}", user);
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    public List<User> getUserList() {
        return userList;
    }

}
