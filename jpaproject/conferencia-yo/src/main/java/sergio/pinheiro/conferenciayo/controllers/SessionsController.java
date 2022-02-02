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

import sergio.pinheiro.conferenciayo.models.Session;
import sergio.pinheiro.conferenciayo.repositories.SessionRepository;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
	@Autowired
	private SessionRepository sessionRepository;

	@GetMapping
	public List<Session> list() {
		return sessionRepository.findAll();
	}

	@GetMapping
	@RequestMapping("{id}")
	public Session get(@PathVariable Long id) {
		return sessionRepository.getById(id);
	}

	@PostMapping
	public Session create(@RequestBody final Session session) {
		return sessionRepository.saveAndFlush(session);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		// also need to check for children records before deleting. Cascade Effect. This
		// method only deletes data without children
		sessionRepository.deleteById(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Session update(@PathVariable Long id, @RequestBody Session session) {
		// because this is a PUT, we expect all attributes to be passed in. A PATCH
		// would only need what
		// TODO: Add validation that all attributes are passed in, otherwise return a
		// 400 bad payload
		Session existingSession = sessionRepository.getById(id);
		BeanUtils.copyProperties(session, existingSession, "session_id"); // if you don't ignore session_id it will
																			// replace the primary key
		return sessionRepository.saveAndFlush(existingSession);
	}

}
