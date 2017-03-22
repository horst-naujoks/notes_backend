package ch.adesso.notes_backend.repository;

import org.springframework.data.repository.CrudRepository;

import ch.adesso.notes_backend.persistence.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, Long> {

}