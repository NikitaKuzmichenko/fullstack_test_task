package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.UserRole;
import com.example.demo.persistence.repository.UserRoleRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class UserRoleHibernateRepository implements UserRoleRepository {

    private static final String GET_ROLE_BY_NAME = "SELECT role FROM UserRole role " +
            "WHERE role.role = :role_name ";

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRoleHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserRole getRoleByName(String roleName) {
        try {
            return  sessionFactory.getCurrentSession().
                    createQuery(GET_ROLE_BY_NAME,UserRole.class).setParameter("role_name",roleName).
                    getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public UserRole getRoleById(long id) {
        return sessionFactory.getCurrentSession().find(UserRole.class,id);
    }

    @Override
    public UserRole create(UserRole role) {
        sessionFactory.getCurrentSession().persist(role);
        return role;
    }
}
