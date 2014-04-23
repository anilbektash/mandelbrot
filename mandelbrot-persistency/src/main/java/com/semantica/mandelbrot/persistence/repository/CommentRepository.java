package com.semantica.mandelbrot.persistence.repository;

import org.springframework.stereotype.Repository;

import com.semantica.mandelbrot.persistence.domain.Comment;

@Repository
public class CommentRepository extends MandelbrotRepository<Comment> {

	public CommentRepository(Class<? extends Comment> type) {
		super(type);
	}

	public CommentRepository() {
		super(Comment.class);
	}

}
