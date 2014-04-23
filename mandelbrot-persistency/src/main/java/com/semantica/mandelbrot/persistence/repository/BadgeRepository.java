package com.semantica.mandelbrot.persistence.repository;

import org.springframework.stereotype.Repository;

import com.semantica.mandelbrot.persistence.domain.Badge;

@Repository
public class BadgeRepository extends MandelbrotRepository<Badge>{

	public BadgeRepository(Class<? extends Badge> type) {
		super(type);
	}
	
	public BadgeRepository(){
		super(Badge.class);
	}

}
