package com.example.demo.persistence.repository.hibernate;

import com.example.demo.persistence.entity.Sensor;
import com.example.demo.persistence.repository.SensorRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class SensorHibernateRepository implements SensorRepository {

    private static final String ALL_FLUIDS_TEXT_FILTER = "left join sensor.type AS type " +
            "left join sensor.measurementsUnit AS mu " +
            "WHERE sensor.name LIKE :predicate " +
            "or sensor.model LIKE :predicate " +
            "or CAST(sensor.lowerOperationalBound as string) LIKE :predicate " +
            "or CAST(sensor.higherOperationalBound as string) LIKE :predicate " +
            "or mu.value LIKE :predicate " +
            "or type.value LIKE :predicate " +
            "or sensor.location LIKE :predicate " +
            "or sensor.description LIKE :predicate " +
            "or sensor.model LIKE :predicate ";

    private static final String COUNT_ALL_WITH_FILTER = "SELECT COUNT(*) FROM Sensor sensor " + ALL_FLUIDS_TEXT_FILTER;
    private static final String GET_ALL_WITH_FILTER = "SELECT sensor FROM Sensor sensor " + ALL_FLUIDS_TEXT_FILTER;

    private final SessionFactory sessionFactory;

    @Autowired
    public SensorHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Sensor> getAll(long limit, long offset, String allFieldsPredicate) {
        return sessionFactory.getCurrentSession().createQuery(GET_ALL_WITH_FILTER,Sensor.class).
                setParameter("predicate","%" + allFieldsPredicate + "%").
                setMaxResults((int) limit).
                setFirstResult((int) offset).
                getResultList();
    }

    @Override
    public List<Sensor> getAll(long limit, long offset) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaQuery<Sensor> query = session.getCriteriaBuilder().createQuery(Sensor.class);
        Root<Sensor> root = query.from(Sensor.class);
        query.select(root);

        return session.createQuery(query)
                .setMaxResults((int) limit)
                .setFirstResult((int) offset)
                .getResultList();
    }

    @Override
    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Sensor> root = query.from(Sensor.class);
        query.select(builder.count(root));

        return session.createQuery(query).getSingleResult();
    }

    @Override
    public long countAll(String allFieldsPredicate) {
        return sessionFactory.getCurrentSession().createQuery(COUNT_ALL_WITH_FILTER,Long.class).
                setParameter("predicate","%" + allFieldsPredicate + "%").
                getSingleResult();
    }

    @Override
    public Sensor getById(long id) {
        return sessionFactory.getCurrentSession().find(Sensor.class, id);
    }

    @Override
    public Sensor create(Sensor event) {
        sessionFactory.getCurrentSession().save(event);
        return event;
    }

    @Override
    public boolean update(Sensor event) {
        if(getById(event.getId()) == null){
            return false;
        }
        sessionFactory.getCurrentSession().merge(event);
        return true;
    }

    @Override
    public boolean delete(long id) {
        Sensor entity = getById(id);
        if(entity == null){
            return false;
        }
        sessionFactory.getCurrentSession().delete(entity);
        return true;
    }

}
