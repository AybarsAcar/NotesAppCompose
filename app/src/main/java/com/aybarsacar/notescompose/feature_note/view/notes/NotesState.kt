package com.aybarsacar.notescompose.feature_note.view.notes

import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.util.NoteOrder
import com.aybarsacar.notescompose.feature_note.domain.util.OrderType


data class NotesState(
  val notes: List<Note> = emptyList(),
  val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
  val isOrderSectionVisible: Boolean = false
)