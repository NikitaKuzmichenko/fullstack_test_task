package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserHibernateRepository implements UserRepository {

    private static final String GET_USER_BY_LOGIN = "SELECT user FROM User user WHERE user.login = :login ";

    private final SessionFactory sessionFactory;

    @Autowired
    public UserHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getById(long id) {
        return sessionFactory.getCurrentSession().find(User.class,id);
    }

    @Override
    public User getUserByLogin(String login) {
        return sessionFactory.getCurrentSession().createQuery(GET_USER_BY_LOGIN,User.class).
                setParameter(login,login).
                getSingleResult();
    }

    @Override
    public User createUser(User user) {
        sessionFactory.getCurrentSession().persist(User.class);
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        if(getById(user.getId()) == null){
            return false;
        }
        sessionFactory.getCurrentSession().update(user);
        return true;
    }

    @Override
    public boolean deleteUserById(long id) {
        User entity = getById(id);
        if(entity == null){
            return false;
        }
        sessionFactory.getCurrentSession().delete(entity);
        return true;
    }
}
