package com.livelygig.api.models

import java.util.UUID
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Describes a Label in LivelyGig.
 * @param id
 * @param value
 * @param `type`
 */
case class LabelDesc(
  id: UUID,
  value: String,
  `type`: String
) {

  def apply(json: String) = parse(json).extract[LabelDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}
