package com.aybarsacar.notescompose.feature_note.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aybarsacar.notescompose.feature_note.view.add_edit_note.AddEditNoteScreen
import com.aybarsacar.notescompose.feature_note.view.notes.NotesScreen
import com.aybarsacar.notescompose.feature_note.view.util.Screen
import com.aybarsacar.notescompose.ui.theme.NotesComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      NotesComposeTheme {

        Surface(
          color = MaterialTheme.colors.background
        ) {

          val navController = rememberNavController()

          NavHost(
            navController = navController,
            startDestination = Screen.NotesScreen.route
          ) {

            composable(route = Screen.NotesScreen.route) {
              NotesScreen(navController = navController)
            }

            composable(
              route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
              arguments = listOf(
                navArgument(name = "noteId") {
                  type = NavType.IntType
                  defaultValue = -1
                },

                navArgument(name = "noteColor") {
                  type = NavType.IntType
                  defaultValue = -1
                }
              )
            ) {
              val color = it.arguments?.getInt("noteColor") ?: -1

              AddEditNoteScreen(navController = navController, noteColor = color)
            }

          }

        }

      }
    }
  }
}