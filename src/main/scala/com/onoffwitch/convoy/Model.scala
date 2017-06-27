package com.onoffwitch.convoy

import com.onoffwitch.convoy.Day.Day
import scala.util.Try

object Day extends Enumeration {
  type Day = Value

  val M, T, W, R, F = Value
}

case class ShipmentId(long: Long) extends AnyVal

case class City(name: String) extends AnyVal

case class InvalidShipmentInput(string: String) extends Exception(string)

object Shipment {
  def apply(string: String): Shipment = {
    string.split(" ").toList match {
      case id :: start :: end :: day :: Nil =>
        Shipment(
          ShipmentId(id.toLong),
          City(start),
          City(end),
          Try(Day.withName(day)).getOrElse(throw InvalidShipmentInput(s"Day ${day} is not a valid day"))
        )
      case _ =>
        throw InvalidShipmentInput(s"${string} is not in the proper shipment format")
    }
  }
}

case class Shipment(id: ShipmentId, start: City, end: City, day: Day)

case class Bundle(shipments: List[Shipment]) {
  override def toString: String = {
    shipments.map(_.id.long).mkString(" ")
  }
}