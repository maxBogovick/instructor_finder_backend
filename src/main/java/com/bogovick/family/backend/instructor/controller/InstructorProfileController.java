package com.bogovick.family.backend.instructor.controller;

import com.bogovick.family.backend.instructor.api.model.InstructorFilterDTO;
import com.bogovick.family.backend.instructor.api.model.InstructorProfileDto;
import com.bogovick.family.backend.instructor.facade.InstructorProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
@CrossOrigin(origins = {"http://localhost:3000/", "https://driving-finder.vercel.app/"})
public class InstructorProfileController {
  private final InstructorProfileFacade instructorProfileFacade;

  @PostMapping
  public InstructorProfileDto createInstructorProfile(@RequestBody InstructorProfileDto profile) {
    return instructorProfileFacade.saveInstructorProfile(profile).orElse(null);
  }

  @PostMapping("/search")
  public Collection<InstructorProfileDto> search(@RequestBody InstructorFilterDTO filter) {
    return instructorProfileFacade.search(filter);
  }

  @GetMapping("/profile/{id}")
  public InstructorProfileDto getById(@PathVariable(name = "id") Long id) {
    return instructorProfileFacade.getById(id);
  }
}
