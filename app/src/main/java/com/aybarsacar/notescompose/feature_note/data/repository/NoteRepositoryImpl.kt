package com.aybarsacar.notescompose.feature_note.data.repository

import com.aybarsacar.notescompose.feature_note.data.data_source.NoteDao
import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl(private val _noteDao: NoteDao) : NoteRepository {

  override fun getNotes(): Flow<List<Note>> {
    return _noteDao.getNotes()
  }

  override suspend fun getNoteById(id: Int): Note? {
    return _noteDao.getNoteById(id)
  }

  override suspend fun insertNote(note: Note) {
    _noteDao.insertNote(note)
  }

  override suspend fun deleteNote(note: Note) {
    _noteDao.deleteNote(note)
  }
}