package com.semantica.mandelbrot.persistence.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class Comment {

	@Id
	private String id;

	@DBRef
	@Indexed
	private Photo source;

	@DBRef
	@Indexed
	private User owner;

	private String content;

	@DBRef(lazy=true)
	private List<User> favers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Photo getSource() {
		return source;
	}

	public void setSource(Photo source) {
		this.source = source;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<User> getFavers() {
		return favers;
	}

	public void setFavers(List<User> favers) {
		this.favers = favers;
	}

}
