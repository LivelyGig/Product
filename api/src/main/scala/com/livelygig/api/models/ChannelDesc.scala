package com.livelygig.api.models

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Describes a Channel in LivelyGig.
 * @param url
 * @param channelType
 */
case class ChannelDesc(
  url: String,
  channelType: String
) {

  def apply(json: String) = parse(json).extract[ChannelDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}
