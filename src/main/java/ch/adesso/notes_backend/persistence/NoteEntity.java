package ch.adesso.notes_backend.persistence;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class NoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private LocalDateTime created = LocalDateTime.now();
	
	@Column(length = 256, nullable = false)
	private String title;

	@Column(length = 1024)
	private String content;

	@Column(length = 64)
	private String color;

	private LocalDateTime due;
	
	@ManyToMany()
	@JoinTable(name="NOTE_2_TAG")
	private Set<TagEntity> tags;

	public long getId() {
		return id;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getDue() {
		return due;
	}

	public void setDue(LocalDateTime due) {
		this.due = due;
	}
	
	public Set<TagEntity> getTags() {
		return tags;
	}
}