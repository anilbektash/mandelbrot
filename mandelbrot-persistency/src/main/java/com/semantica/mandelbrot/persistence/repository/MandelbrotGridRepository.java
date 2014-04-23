package com.semantica.mandelbrot.persistence.repository;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@Repository
public abstract class MandelbrotGridRepository<T> extends MandelbrotRepository<T>{
	
	private Log LOG = LogFactory.getLog(getClass());
	
	@Autowired
	@Qualifier("gridFsTemplate")
	protected GridFsOperations gridFSTemplate;

	public MandelbrotGridRepository(Class<? extends T> type) {
		super(type);
	}
	
	public void save(T object) {
		LOG.debug("Persisting: " + object.toString());
		mongoTemplate.insert(object);
//		gridFSTemplate.sto
	}
	
	public void addImageToUserById(String userName, InputStream imageIS) {

		// create a new metadata object.
		DBObject metaData = new BasicDBObject();
		// we can put additional metadata here.
		// metaData.put("extra1", "anything 1");

		gridFSTemplate.store(imageIS, userName, metaData);

		// Query query = new Query(Criteria.where("_id").is(id));
		// Update update = new Update();
		// update.addToSet("image", image);
		// mongoTemplate.updateFirst(query, update, User.class);
	}

	public void updateImageToUserById(String userName, InputStream newImageIS) {
		// delete existing image from gridfs if exists.
		gridFSTemplate.delete(new Query().addCriteria(Criteria
				.where("filename").is(userName)));
		// add new image stream with username
		gridFSTemplate.store(newImageIS, userName, new BasicDBObject());
	}

	public InputStream getImageByUsername(String username) {
		// find user's uploaded image...
		GridFSDBFile gridFSDBFile = gridFSTemplate.findOne(new Query()
				.addCriteria(Criteria.where("filename").is(username)));

		if (gridFSDBFile != null) {
			return gridFSDBFile.getInputStream();
		} else {
			return null;
		}
	}
	
}
