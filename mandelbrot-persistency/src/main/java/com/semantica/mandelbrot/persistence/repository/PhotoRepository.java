package com.semantica.mandelbrot.persistence.repository;

import org.springframework.stereotype.Repository;

import com.semantica.mandelbrot.persistence.domain.Photo;

@Repository
public class PhotoRepository extends MandelbrotGridRepository<Photo> {

	public PhotoRepository(Class<? extends Photo> type) {
		super(type);
	}

	public PhotoRepository() {
		super(Photo.class);
	}

}
