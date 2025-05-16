package com.hugo.comermelhor.util

fun <E> MutableList<E>.replaceAt(index: Int, newElement: E) {
    this[index] = newElement
}