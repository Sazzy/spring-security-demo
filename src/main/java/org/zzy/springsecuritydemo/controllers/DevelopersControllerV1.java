package org.zzy.springsecuritydemo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zzy.springsecuritydemo.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DevelopersControllerV1 {
	private final List<Developer> developers = Stream.of(
			new Developer(1L, "Ivan", "Ivanov"),
			new Developer(2L, "Sergey", "Sergeyev"),
			new Developer(3L, "Petr", "Petrov")
	).collect(Collectors.toList());

	@PreAuthorize("hasAuthority('developers:read')")
	@GetMapping
	public List<Developer> getAll() {
		return developers;
	}

	@PreAuthorize("hasAuthority('developers:write')")
	@GetMapping("/{id}")
	public Developer getById(@PathVariable Long id) {
		return developers.stream().filter(developer -> id.equals(developer.getId())).findFirst().orElse(null);
	}

	@PreAuthorize("hasAuthority('developers:write')")
	@PostMapping
	public Developer create(@RequestBody Developer developer) {
		this.developers.add(developer);
		return developer;
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		developers.removeIf(developer -> id.equals(developer.getId()));
	}
}
