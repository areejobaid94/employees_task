package com.example.demo.controllers;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Department;
import com.example.demo.entities.Note;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
                note = noteRepository.save(note);
                return new ResponseEntity(note,HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // get my notes
    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAll() {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // return all notes.
                return new ResponseEntity(userDetails.getNote(), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
