package com.aybarsacar.notescompose.feature_note.domain.use_case

import com.aybarsacar.notescompose.feature_note.domain.model.InvalidNoteException
import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.repository.NoteRepository


class AddNote(private val _repository: NoteRepository) {

  @Throws(InvalidNoteException::class)
  suspend operator fun invoke(note: Note) {

    if (note.title.isBlank()) {
      throw InvalidNoteException("Title of the note cannot be empty")
    }

    if (note.content.isBlank()) {
      throw InvalidNoteException("Content of the note cannot be empty")
    }

    _repository.insertNote(note)
  }
}