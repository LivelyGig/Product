package com.livelygig.api.models

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Container for Contacts in LivelyGig.
 * @param channels
 */
case class ContactDesc(
  channels: List[ChannelDesc]
) {

  def apply(json: String) = parse(json).extract[ContactDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}
