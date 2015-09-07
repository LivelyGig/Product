package com.livelygig.webapp.shared


trait Api{
  def list(path: String): Seq[String]
}