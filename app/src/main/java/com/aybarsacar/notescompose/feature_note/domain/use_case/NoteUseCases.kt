package com.aybarsacar.notescompose.feature_note.domain.use_case


/**
 * Unit of Work
 * used to inject all the use cases for notes
 */
data class NoteUseCases(
  val getNotes: GetNotes,
  val deleteNote: DeleteNote,
  val addNote: AddNote
)