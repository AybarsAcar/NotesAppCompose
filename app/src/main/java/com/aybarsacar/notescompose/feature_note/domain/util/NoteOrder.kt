package com.aybarsacar.notescompose.feature_note.domain.util

/**
 * what type used to order the notes
 */
sealed class NoteOrder(val orderType: OrderType) {

  class Title(orderType: OrderType) : NoteOrder(orderType)

  class Date(orderType: OrderType) : NoteOrder(orderType)

  class Color(orderType: OrderType) : NoteOrder(orderType)


  fun copy(orderType: OrderType): NoteOrder {
    return when (this) {
      is Title -> Title(orderType)
      is Date -> Date(orderType)
      is Color -> Color(orderType)
    }
  }
}