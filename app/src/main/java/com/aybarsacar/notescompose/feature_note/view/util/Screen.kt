package com.aybarsacar.notescompose.feature_note.view.util


sealed class Screen(val route: String) {

  object NotesScreen : Screen("notes_screen")

  object AddEditNoteScreen : Screen("add_edit_note_screen")

}