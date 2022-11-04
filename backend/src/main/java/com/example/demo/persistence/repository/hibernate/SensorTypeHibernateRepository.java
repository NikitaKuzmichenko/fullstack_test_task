package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.SensorType;
import com.example.demo.persistence.repository.SensorTypeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SensorTypeHibernateRepository implements SensorTypeRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public SensorTypeHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<SensorType> getAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaQuery<SensorType> query = session.getCriteriaBuilder().createQuery(SensorType.class);
        Root<SensorType> root = query.from(SensorType.class);
        query.select(root);

        return session.createQuery(query)
                .getResultList();
    }

    @Override
    public SensorType getById(long id) {
        return sessionFactory.getCurrentSession().find(SensorType.class, id);
    }

    @Override
    public SensorType create(SensorType event) {
        sessionFactory.getCurrentSession().save(event);
        return event;
    }

    @Override
    public boolean update(SensorType event) {
        if(getById(event.getId()) == null){
            return false;
        }
        sessionFactory.getCurrentSession().merge(event);
        return true;
    }

    @Override
    public boolean delete(long id) {
        SensorType entity = getById(id);
        if(entity == null){
            return false;
        }
        sessionFactory.getCurrentSession().delete(entity);
        return true;
    }

}
