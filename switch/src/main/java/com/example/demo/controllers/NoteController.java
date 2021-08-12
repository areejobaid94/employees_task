package com.example.demo.controllers;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Note;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= "*")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    // save a notes for the user.
    @PostMapping("/note")
    public ResponseEntity add(@RequestBody Note note) {
        try{
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                note.setUser(userDetails);
                noteRepository.save(note);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
