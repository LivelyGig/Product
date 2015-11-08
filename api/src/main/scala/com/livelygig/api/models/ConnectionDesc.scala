package com.livelygig.api.models

import java.util.UUID
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Connections two Agents in LivelyGig.
 * @param src
 * @param target
 * @param label
 */
case class ConnectionDesc(
  src: UUID,
  target: UUID,
  label: LabelDesc
) {

  def apply(json: String) = parse(json).extract[ConnectionDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}
