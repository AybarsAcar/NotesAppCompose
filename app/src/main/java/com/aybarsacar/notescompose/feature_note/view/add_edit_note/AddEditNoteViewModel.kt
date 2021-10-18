package com.aybarsacar.notescompose.feature_note.view.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.aybarsacar.notescompose.feature_note.domain.model.Note
import com.aybarsacar.notescompose.feature_note.domain.use_case.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject


class AddEditNoteViewModel @Inject constructor(private val _noteUseCases: NoteUseCases) : ViewModel() {

  private val _noteTitle = mutableStateOf(NoteTextFieldState())
  val noteTitle: State<NoteTextFieldState> = _noteTitle

  private val _noteContent = mutableStateOf(NoteTextFieldState())
  val noteContent: State<NoteTextFieldState> = _noteContent

  private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
  val noteColor: State<Int> = _noteColor


  //
//  private val _eventFlow = MutableSharedFlow

}