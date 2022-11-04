package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.RefreshToken;
import com.example.demo.persistence.repository.RefreshTokenRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenHibernateRepository implements RefreshTokenRepository {

    private static final String GET_REFRESH_TOKEN_BY_TOKEN_VALUE = "SELECT token FROM RefreshToken token " +
            "WHERE token.value = :token_value ";

    private static final String GET_REFRESH_TOKEN_BY_USER_ID = "SELECT token FROM RefreshToken token " +
            "WHERE token.userId = :userId ";

    private static final String GET_REFRESH_TOKEN_BY_USER_LOGIN = "SELECT token FROM RefreshToken token " +
            "left join token.userId as user" +
            "WHERE user.login = :login ";

    private final SessionFactory sessionFactory;

    @Autowired
    public RefreshTokenHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public RefreshToken getById(long id) {
        return sessionFactory.getCurrentSession().find(RefreshToken.class, id);
    }

    @Override
    public RefreshToken getByUserId(long id) {
        return sessionFactory.getCurrentSession().createQuery(GET_REFRESH_TOKEN_BY_USER_ID,RefreshToken.class).
                setParameter("userId",id).
                getSingleResult();
    }

    @Override
    public RefreshToken getByUserLogin(String login) {
        return sessionFactory.getCurrentSession().createQuery(GET_REFRESH_TOKEN_BY_USER_LOGIN,RefreshToken.class).
                setParameter("login",login).
                getSingleResult();
    }

    @Override
    public RefreshToken getByToken(String token) {
        return sessionFactory.getCurrentSession().createQuery(GET_REFRESH_TOKEN_BY_TOKEN_VALUE,RefreshToken.class).
                setParameter("token_value",token).
                getSingleResult();
    }

    @Override
    public RefreshToken createToken(RefreshToken token) {
        sessionFactory.getCurrentSession().persist(token);
        return token;
    }

    @Override
    public boolean updateToken(RefreshToken token) {
        if(getById(token.getId()) == null){
            return false;
        }
        sessionFactory.getCurrentSession().update(token);
        return true;
    }
}
