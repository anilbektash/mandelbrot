package com.semantica.mandelbrot.persistence.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public abstract class MandelbrotRepository<T> {

	private Log LOG = LogFactory.getLog(getClass());

	@Autowired
	protected MongoTemplate mongoTemplate;

	private Class<? extends T> type;

	public MandelbrotRepository(Class<? extends T> type) {
		this.type = type;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void save(T object) {
		LOG.debug("Persisting: " + object.toString());
		mongoTemplate.insert(object);
	}

	public long count() {
		return mongoTemplate.count(new Query(), type);
	}

	public T findOne(ObjectId id) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),
				type);
	}

	public List<? extends T> findAll() {
		return mongoTemplate.findAll(type);
	}

	/**
	 * Updates multiple id's for same field name. NOTICE that ids and values
	 * lists are parallel.
	 * 
	 * @param ids
	 *            id's list
	 * @param fieldName
	 *            the only one field name
	 * @param values
	 *            values list.
	 */
	public void update(List<ObjectId> ids, String fieldName, List<Object> values) {
		if (ids.size() != values.size()) {
			LOG.error("IDs and values sizes must be equal!");
			throw new IllegalArgumentException(
					"IDs and values sizes must be equal!");
		}
		for (int i = 0; i < ids.size(); i++) {
			updateObject(ids.get(i), fieldName, values.get(i));
		}
	}

	public void updateObject(ObjectId id, String fieldName, Object value) {
		LOG.debug("Updating: " + value.toString());
		Query query = new Query(Criteria.where("_id").is(id));
		Update updateFunction = Update.update(fieldName, value);
		mongoTemplate.updateFirst(query, updateFunction, type);
	}

	public boolean updateObject(ObjectId id, List<String> fieldNames,
			List<Object> values) {
		if (fieldNames.size() < 1 || fieldNames.size() != values.size()) {
			throw new IllegalArgumentException(
					"fieldNames and values sizes must be equal!");
		}
		LOG.debug("MongoDB: Multiple fields updating");
		Query query = new Query(Criteria.where("_id").is(id));
		Update updateFunction = Update.update(fieldNames.get(0), values.get(0));
		for (int i = fieldNames.size(); --i > 0;) {
			updateFunction.set(fieldNames.get(i), values.get(i));
		}
		return mongoTemplate.updateFirst(query, updateFunction, type).getN() > 0;
	}

	public void removeByID(ObjectId id) {
		mongoTemplate.remove(mongoTemplate.findById(id, type));
	}

	public void removeAll() {
		mongoTemplate.remove(new Query(), type);
	}

}
