package com.livelygig.api

class SharedList() {
  var items = Seq(
    "One",
    "Two",
    "Three"
  )
  
  def addItem(item: String): Seq[String] = {
    items = items :+ item
    items
  }
  
  def removeItem(item: String): Seq[String] = {
    items = items.filterNot(_ == item)
    items
  }
  
  def getItems(): Seq[String] = {
    items
  }

}