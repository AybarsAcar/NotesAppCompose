package com.aybarsacar.notescompose.feature_note.view.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.notescompose.feature_note.domain.model.InvalidNoteException
import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
  private val _noteUseCases: NoteUseCases,
  savedStateHandle: SavedStateHandle // contains the navigation arguments
) : ViewModel() {

  private val _noteTitle = mutableStateOf(
    NoteTextFieldState(
      hint = "Enter title..."
    )
  )
  val noteTitle: State<NoteTextFieldState> = _noteTitle

  private val _noteContent = mutableStateOf(
    NoteTextFieldState(
      hint = "Enter your content..."
    )
  )
  val noteContent: State<NoteTextFieldState> = _noteContent

  private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
  val noteColor: State<Int> = _noteColor


  // 1 time events that do not represent the state of the UI - like snack bars & feedbacks
  private val _eventFlow = MutableSharedFlow<UiEvent>()
  val eventFlow = _eventFlow.asSharedFlow()


  private var _currentNoteId: Int? = null


  init {
    savedStateHandle.get<Int>("noteId")?.let { noteId ->
      if (noteId != -1) {
        viewModelScope.launch {
          _noteUseCases.getNote(noteId)?.also {
            _currentNoteId = it.id
            _noteTitle.value = noteTitle.value.copy(text = it.title, isHintVisible = false)
            _noteContent.value = _noteContent.value.copy(text = it.content, isHintVisible = false)
            _noteColor.value = it.color
          }
        }
      }
    }
  }


  fun onEvent(event: AddEditNoteEvent) {

    when (event) {

      is AddEditNoteEvent.EnteredTitle -> {
        _noteTitle.value = _noteTitle.value.copy(text = event.value)
      }

      is AddEditNoteEvent.ChangeTitleFocus -> {
        _noteTitle.value =
          _noteTitle.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
      }

      is AddEditNoteEvent.EnteredContent -> {
        _noteContent.value = _noteContent.value.copy(text = event.value)
      }

      is AddEditNoteEvent.ChangeContentFocus -> {
        _noteContent.value =
          _noteContent.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
      }

      is AddEditNoteEvent.ChangeColor -> {
        _noteColor.value = event.color
      }

      is AddEditNoteEvent.SaveNote -> {
        // add note use case
        viewModelScope.launch {
          try {

            _noteUseCases.addNote(
              Note(
                _noteTitle.value.text,
                _noteContent.value.text,
                System.currentTimeMillis(),
                _noteColor.value,
                _currentNoteId
              )
            )

            _eventFlow.emit(UiEvent.SaveNote)

          } catch (e: InvalidNoteException) {
            _eventFlow.emit(
              UiEvent.ShowSnackBar(
                message = e.message ?: "Could not save the note"
              )
            )
          }
        }
      }
    }
  }


  sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
  }

}