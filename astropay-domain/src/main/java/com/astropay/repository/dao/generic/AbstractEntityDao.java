package com.astropay.repository.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.astropay.repository.entities.generic.AbstractEntity;

/**
 *
 * @author echinelli
 *
 * @param <T>
 *            Entidad del modelo que extiende de AbstractEntity
 * @param <ID>
 *            Tipo del campo ID de la entidad
 */
public abstract class AbstractEntityDao<T extends AbstractEntity<ID>, ID extends Serializable>
		extends AbstractHibernateDao {

	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void loadGenericClass() {
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	protected Class<T> getClazz() {
		return this.clazz;
	}

	protected DetachedCriteria createCriteria(String classAlias) {
		return DetachedCriteria.forClass(this.clazz,
				classAlias == null ? this.clazz.getSimpleName().toLowerCase()
						: classAlias);
	}

	@SuppressWarnings("unchecked")
	public T read(ID id) {
		return (T) this.getCurrentSession().get(this.clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> read(List<ID> ids) {
		return this.getSession().add(Restrictions.in("id", ids)).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> readAll() {
		return this.getSession()
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	/*
	 * @SuppressWarnings("unchecked") protected List<T> readAll() { return
	 * this.getSession().list(); }
	 */

	protected T load(ID id) {
		T entity = this.read(id);
		if (entity == null) {
			throw new javax.persistence.EntityNotFoundException(
					"Unable to find " + this.clazz.getSimpleName()
							+ " with id #" + id);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByProperty(String property, Object value) {
		return this.getSession().add(Restrictions.eq(property, value))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByProperties(Map<String, Object> properties) {
		Criteria criteria = this.getSession();
		for (Entry<String, Object> property : properties.entrySet()) {
			if (property.getValue() != null
					&& property.getValue() instanceof List<?>) {
				criteria = criteria.add(Restrictions.in(property.getKey(),
						(Collection<Object>) property.getValue()));
			} else {
				criteria = criteria.add(Restrictions.eq(property.getKey(),
						property.getValue()));
			}
		}
		return criteria.list();
	}

	protected Criteria join(String associationPath, String alias,
			JoinType joinType) {
		Criteria criteria = this.getSession();
		criteria.createAlias(associationPath, alias, joinType);
		return criteria;
	}

	protected Criteria getSession() {
		return this.getCurrentSession().createCriteria(this.clazz);
	}

	protected T readByProperty(String property, Object value) {
		List<T> result = this.findByProperty(property, value);
		return result.isEmpty() ? null : result.get(0);
	}

	protected T loadByProperty(String property, Object value) {
		List<T> result = this.findByProperty(property, value);
		this.validateUnique(result);
		return result.isEmpty() ? null : result.get(0);
	}

	protected T readByProperties(Map<String, Object> properties) {
		List<T> result = this.findByProperties(properties);
		this.validateUnique(result);
		return result.isEmpty() ? null : result.get(0);
	}

	protected T loadByProperties(Map<String, Object> properties) {
		List<T> result = this.findByProperties(properties);
		if (result.isEmpty()) {
			StringBuilder sb = new StringBuilder("Unable to find ");
			sb.append(this.clazz.getSimpleName()).append("with properties: ");
			for (Entry<String, Object> property : properties.entrySet()) {
				sb.append(" - ").append(property.getKey()).append(": ")
						.append(properties.get(property.getValue()));
			}
			throw new javax.persistence.EntityNotFoundException(sb.toString());
		}
		this.validateUnique(result);
		return result.get(0);
	}

	private void validateUnique(List<T> result) {
		if (result.size() > 1) {
			throw new javax.persistence.NonUniqueResultException(
					"Expected single result, but got " + result);
		}
	}

	public void save(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	protected void update(T entity) {
		if (entity.getId() == null) {
			throw new javax.persistence.PersistenceException("Update entity "
					+ entity.getClass() + " without id");
		}
		this.getCurrentSession().saveOrUpdate(entity);
	}

	public void refresh(T entity) {
		this.getCurrentSession().refresh(entity);
	}

	public void merge(T entity) {
		this.getCurrentSession().merge(entity);
	}

	public void flush() {
		this.getCurrentSession().flush();
	}

	protected void delete(T entity) {
		this.getCurrentSession().delete(entity);
	}

	public void delete(ID id) {
		T entity = this.load(id);
		this.getCurrentSession().delete(entity);
	}
}
