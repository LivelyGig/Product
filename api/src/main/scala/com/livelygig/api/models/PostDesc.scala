package com.livelygig.api.models

import java.util.UUID
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Describes a Post in LivelyGig.
 * @param content
 * @param contentType
 * @param labels
 * @param src
 * @param targets
 */
case class PostDesc(
  content: String,
  contentType: String,
  labels: List[UUID],
  src: UUID,
  targets: List[UUID]
) {

  def apply(json: String) = parse(json).extract[PostDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}

