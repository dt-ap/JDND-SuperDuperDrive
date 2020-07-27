package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exception.NoteException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

  private final NoteMapper mapper;
  private final UserService userService;

  public NoteService(NoteMapper mapper, UserService userService) {
    this.mapper = mapper;
    this.userService = userService;
  }

  public List<Note> getNotesByCurrentUser() {
    return mapper.findNotesByUserId(userService.getCurrentUser().getUserId());
  }

  public int create(Note note) throws NoteException {
    try {
      var newNote = note.setUserId(userService.getCurrentUser().getUserId());
      return mapper.insert(newNote);
    } catch (DuplicateKeyException ex) {
      throw new NoteException("'" + note.getTitle() + "' already exists.");
    }
  }

  public int update(Note note) {
    var newNote = note.setUserId(userService.getCurrentUser().getUserId());
    return mapper.update(newNote);
  }

  public int delete(Integer id) throws NoteException {
    var note = Note.fromId(id).setUserId(userService.getCurrentUser().getUserId());
    var deleted = mapper.delete(note);
    if (deleted <= 0) {
      throw new NoteException("There was an error when deleting note.");
    }

    return deleted;
  }

}
