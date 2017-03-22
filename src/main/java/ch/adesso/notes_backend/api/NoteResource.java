package ch.adesso.notes_backend.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.adesso.notes_backend.model.Note;
import ch.adesso.notes_backend.persistence.NoteEntity;
import ch.adesso.notes_backend.persistence.TagEntity;
import ch.adesso.notes_backend.repository.NoteRepository;
import ch.adesso.notes_backend.repository.TagRepository;
import ch.adesso.notes_backend.util.NotFoundException;

@Controller
@RequestMapping("/note")
public class NoteResource {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	TagRepository tagRepository;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Note> getAll() {

		List<Note> result = new ArrayList<>();
		noteRepository.getAll().forEach(e -> {
			result.add(transform(e));
		});
		return result;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Note get(@PathVariable("id") long id) {

		NoteEntity noteEntity = noteRepository.findOne(id);
		if (noteEntity == null)
			throw new NotFoundException();

		return transform(noteEntity);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String add(@RequestBody Note note) {

		NoteEntity noteEntity = transform(note);

		noteRepository.save(noteEntity);
		return "added";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable("id") long id, @RequestBody Note note) {

		NoteEntity noteEntity = noteRepository.findOne(id);
		if (noteEntity == null)
			throw new NotFoundException();

		setAttributes(noteEntity, note);

		noteRepository.save(noteEntity);

		return "updated";
	}

	@RequestMapping(path = "/{noteId}/{tagId}", method = RequestMethod.PUT)
	public @ResponseBody String assignTag(@PathVariable("noteId") long noteId, @PathVariable("tagId") long tagId) {

		NoteEntity noteEntity = noteRepository.findOne(noteId);
		if (noteEntity == null)
			throw new NotFoundException();

		TagEntity tagEntity = tagRepository.findOne(noteId);
		if (tagEntity == null)
			throw new NotFoundException();

		noteEntity.getTags().add(tagEntity);

		noteRepository.save(noteEntity);

		return "updated";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") long id) {

		NoteEntity noteEntity = noteRepository.findOne(id);
		if (noteEntity == null)
			throw new NotFoundException();

		noteRepository.delete(noteEntity);

		return "deleted";
	}

	private Note transform(NoteEntity noteEntity) {

		Note note = new Note();
		note.setId(noteEntity.getId());
		note.setCreated(noteEntity.getCreated());
		note.setTitle(noteEntity.getTitle());
		note.setContent(noteEntity.getContent());
		note.setColor(noteEntity.getColor());
		note.setDue(noteEntity.getDue());
		noteEntity.getTags().forEach(t -> note.getTagIds().add(t.getId()));
		return note;
	}

	private NoteEntity transform(Note note) {

		NoteEntity noteEntity = new NoteEntity();
		setAttributes(noteEntity, note);

		return noteEntity;
	}

	private void setAttributes(NoteEntity noteEntity, Note note) {

		noteEntity.setTitle(note.getTitle());
		noteEntity.setContent(note.getContent());
		noteEntity.setColor(note.getColor());
		noteEntity.setDue(note.getDue());
	}
}