package com.livelygig.api.models

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


/**
 * Describes a LivelyGig dataset.
 * @param agents
 * @param labels
 * @param connections
 */
case class DataSetDesc(
  agents: List[AgentDesc],
  labels: List[LabelDesc],
  connections: List[ConnectionDesc]
) {

  def apply(json: String) = parse(json).extract[DataSetDesc]

  /**
   * Serializes to JSON.
   * @return JSON String.
   */
  def toJson = write(this)

  implicit val formats = DefaultFormats
}