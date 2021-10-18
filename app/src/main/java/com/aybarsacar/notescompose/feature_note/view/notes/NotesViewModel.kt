package com.aybarsacar.notescompose.feature_note.view.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.use_case.NoteUseCases
import com.aybarsacar.notescompose.feature_note.domain.util.NoteOrder
import com.aybarsacar.notescompose.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(private val _noteUseCases: NoteUseCases) : ViewModel() {

  private val _state = mutableStateOf(NotesState())
  val state: State<NotesState> = _state // readonly public state


  private var _recentlyDeletedNote: Note? = null

  private var _getNotesJob: Job? = null


  init {
    getNotes(NoteOrder.Date(OrderType.Descending))
  }


  fun onEvent(event: NotesEvent) {

    when (event) {

      is NotesEvent.Order -> {

        // check if the order has not changed
        if (
          state.value.noteOrder::class == event.noteOrder::class &&
          state.value.noteOrder.orderType == event.noteOrder.orderType::class
        ) {
          return
        }

        getNotes(event.noteOrder)
      }

      is NotesEvent.DeleteNote -> {
        viewModelScope.launch {
          _noteUseCases.deleteNote(event.note)

          _recentlyDeletedNote = event.note
        }
      }

      is NotesEvent.RestoreNote -> {

        viewModelScope.launch {
          _noteUseCases.addNote(_recentlyDeletedNote ?: return@launch)

          _recentlyDeletedNote = null
        }
      }

      is NotesEvent.ToggleOrderSection -> {
        _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
      }

    }
  }

  /**
   * gets the notes passed in the note order
   */
  private fun getNotes(noteOrder: NoteOrder) {

    _getNotesJob?.cancel()

    _getNotesJob = _noteUseCases.getNotes(noteOrder).onEach {
      _state.value = state.value.copy(notes = it, noteOrder = noteOrder)
    }
      .launchIn(viewModelScope)

  }
}