package com.semantica.mandelbrot.persistence.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Photo {

	@Id
	private String id;

	private String name;
	
	private String explanation;

	//TODO: @Transient 
	//probably will be managed by gridFS.
	private byte[] photoContent;
	
	@DBRef
	private User owner;

//	@DBRef(lazy=true)
//	private List<Comment> comments;
	
	@DBRef(lazy = true)
	private List<User> favers;

//	public List<Comment> getComments() {
//		return comments;
//	}

	public String getExplanation() {
		return explanation;
	}

	public List<User> getFavers() {
		return favers;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public byte[] getPhotoContent() {
		return photoContent;
	}

//	public void setComments(List<Comment> comments) {
//		this.comments = comments;
//	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setFavers(List<User> favers) {
		this.favers = favers;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setPhotoContent(byte[] photoContent) {
		this.photoContent = photoContent;
	}

}
