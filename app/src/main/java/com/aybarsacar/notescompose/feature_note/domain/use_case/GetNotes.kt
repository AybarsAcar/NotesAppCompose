package com.aybarsacar.notescompose.feature_note.domain.use_case

import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.repository.NoteRepository
import com.aybarsacar.notescompose.feature_note.domain.util.NoteOrder
import com.aybarsacar.notescompose.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * use case to get notes
 */
class GetNotes(private val _repository: NoteRepository) {

  /**
   * get all notes
   * default order is order by date in descending order
   */
  operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {

    return _repository.getNotes().map { notes ->

      when (noteOrder.orderType) {

        is OrderType.Ascending -> {

          when (noteOrder) {
            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedBy { it.createdAt }
            is NoteOrder.Color -> notes.sortedBy { it.color }
          }

        }

        is OrderType.Descending -> {

          when (noteOrder) {
            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedByDescending { it.createdAt }
            is NoteOrder.Color -> notes.sortedByDescending { it.color }
          }
        }

      }
    }
  }
}