package ch.adesso.notes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.adesso.notes_backend.persistence.NoteEntity;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

	static final String SELECT_ALL = "select note from NoteEntity note order by note.created desc";
	
    @Query(SELECT_ALL)
    List<NoteEntity> getAll();
}