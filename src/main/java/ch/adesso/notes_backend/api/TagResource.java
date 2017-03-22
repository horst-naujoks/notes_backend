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

import ch.adesso.notes_backend.model.Tag;
import ch.adesso.notes_backend.persistence.TagEntity;
import ch.adesso.notes_backend.repository.TagRepository;
import ch.adesso.notes_backend.util.NotFoundException;

@Controller
@RequestMapping("/tag")
public class TagResource {

	@Autowired
	TagRepository tagRepository;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Tag> getAll() {

		List<Tag> result = new ArrayList<>();
		tagRepository.findAll().forEach(e -> {
			result.add(transform(e));
		});
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String add(@RequestBody Tag tag) {

		TagEntity tagEntity = transform(tag);
		
		tagRepository.save(tagEntity);
		return "added";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable("id") long id, @RequestBody Tag tag) {

		TagEntity tagEntity = tagRepository.findOne(id);
		if (tagEntity == null)
			throw new NotFoundException();
		setAttributes(tag, tagEntity);
		tagRepository.save(tagEntity);
		return "updated";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") long id) {

		TagEntity tagEntity = tagRepository.findOne(id);
		if (tagEntity == null)
			throw new NotFoundException();
		tagRepository.delete(tagEntity);
		return "deleted";
	}
	
	private Tag transform(TagEntity tagEntity) {
		
		Tag tag = new Tag();
		tag.setId(tagEntity.getId());
		tag.setName(tagEntity.getName());
		tag.setColor(tagEntity.getColor());
		return tag;
	}
	
	private TagEntity transform(Tag tag) {
		
		TagEntity tagEntity = new TagEntity();
		setAttributes(tag, tagEntity);
		return tagEntity;
	}

	private void setAttributes(Tag tag, TagEntity tagEntity) {
		tagEntity.setName(tag.getName());
		tagEntity.setColor(tag.getColor());
	}
}