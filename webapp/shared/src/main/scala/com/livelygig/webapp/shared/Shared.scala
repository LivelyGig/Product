package com.livelygig.webapp.shared


trait Api{
  def addItem(item: String): Seq[String]
  def removeItem(item: String): Seq[String]
  def list(): Seq[String]
}