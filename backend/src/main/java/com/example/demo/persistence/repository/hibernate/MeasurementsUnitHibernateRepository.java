package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.MeasurementsUnit;
import com.example.demo.persistence.repository.MeasurementsUnitRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MeasurementsUnitHibernateRepository implements MeasurementsUnitRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public MeasurementsUnitHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MeasurementsUnit> getAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaQuery<MeasurementsUnit> query = session.getCriteriaBuilder().createQuery(MeasurementsUnit.class);
        Root<MeasurementsUnit> root = query.from(MeasurementsUnit.class);
        query.select(root);

        return session.createQuery(query)
                .getResultList();
    }

    @Override
    public MeasurementsUnit getById(long id) {
        return sessionFactory.getCurrentSession().find(MeasurementsUnit.class, id);
    }

    @Override
    public MeasurementsUnit create(MeasurementsUnit event) {
        sessionFactory.getCurrentSession().save(event);
        return event;
    }

    @Override
    public boolean update(MeasurementsUnit event) {
        if(getById(event.getId()) == null){
            return false;
        }
        sessionFactory.getCurrentSession().merge(event);
        return true;
    }

    @Override
    public boolean delete(long id) {
        MeasurementsUnit entity = getById(id);
        if(entity == null){
            return false;
        }
        sessionFactory.getCurrentSession().delete(entity);
        return true;
    }
}
