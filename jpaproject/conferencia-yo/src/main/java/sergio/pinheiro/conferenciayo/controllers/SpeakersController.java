package sergio.pinheiro.conferenciayo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.conferenciayo.models.Speaker;
import sergio.pinheiro.conferenciayo.repositories.SpeakerRepository;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
	@Autowired
	private SpeakerRepository speakerRepository;

	@GetMapping
	public List<Speaker> list() {
		return speakerRepository.findAll();
	}

	@GetMapping
	@RequestMapping("{id}")
	public Speaker get(@PathVariable Long id) {
		return speakerRepository.getById(id);
	}

	@PostMapping
	public Speaker create(@RequestBody final Speaker speaker) {
		return speakerRepository.saveAndFlush(speaker);
	}

	@RequestMapping(value = { "id" }, method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		speakerRepository.deleteById(id);

	}

	@RequestMapping(value = { "id" }, method = RequestMethod.PUT)
	public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
		// Add validation that all attributes are to be passed in, otherwise return a
		// 400 bad request
		Speaker existingSpeaker = speakerRepository.getById(id);
		BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
		return speakerRepository.saveAndFlush(existingSpeaker);
	}
}
