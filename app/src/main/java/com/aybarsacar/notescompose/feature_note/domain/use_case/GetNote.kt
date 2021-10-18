package com.aybarsacar.notescompose.feature_note.domain.use_case

import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.repository.NoteRepository


class GetNote(private val _repository: NoteRepository) {

  suspend operator fun invoke(id: Int): Note? {

    return _repository.getNoteById(id)

  }

}