package com.visible.thred.documentAi.model;

import java.time.LocalDateTime;
import java.util.Arrays;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int wordCount;
	private LocalDateTime uploadTime;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] filedata;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	private LocalDateTime updatedTime;
	public Document() {
		super();
	}

	public Document(String name, int wordCount, LocalDateTime uploadTime, byte[] filedata, User user) {
		super();
		this.name = name;
		this.wordCount = wordCount;
		this.uploadTime = uploadTime;
		this.filedata = filedata;
		this.user = user;
	}

	public Document(Long id, String name, int wordCount, LocalDateTime uploadTime, byte[] filedata, User user) {
		super();
		this.id = id;
		this.name = name;
		this.wordCount = wordCount;
		this.uploadTime = uploadTime;
		this.filedata = filedata;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(filedata);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uploadTime == null) ? 0 : uploadTime.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + wordCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (!Arrays.equals(filedata, other.filedata))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uploadTime == null) {
			if (other.uploadTime != null)
				return false;
		} else if (!uploadTime.equals(other.uploadTime))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (wordCount != other.wordCount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + name + ", wordCount=" + wordCount + ", uploadTime=" + uploadTime
				+ ", filedata=" + Arrays.toString(filedata) + ", user=" + user + "]";
	}

}
