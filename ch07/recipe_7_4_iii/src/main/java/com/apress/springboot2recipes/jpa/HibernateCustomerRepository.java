package com.apress.springboot2recipes.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
class HibernateCustomerRepository implements CustomerRepository {

	private final SessionFactory sessionFactory;

	HibernateCustomerRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

  @Override
	public List<Customer> findAll() {
		return getSession().createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

	@Override
	public Customer findById(long id) {
		return getSession().find(Customer.class, id);
	}

	@Override
	public Customer save(Customer customer) {
		getSession().persist(customer);
		return customer;
	}
}
