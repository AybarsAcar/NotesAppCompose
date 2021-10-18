package com.aybarsacar.notescompose.feature_note.domain.util


sealed class OrderType {
  object Ascending : OrderType()
  object Descending : OrderType()
}
