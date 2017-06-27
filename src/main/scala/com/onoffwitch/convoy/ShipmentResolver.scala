package com.onoffwitch.convoy

import com.onoffwitch.convoy.Day.Day
import scala.annotation.tailrec

class ShipmentResolver(shipments: List[Shipment]) {
  implicit class RichDay(day: Day) {
    def nextDay: Day = Day(day.id + 1)

    def prevDay: Day = Day(day.id - 1)
  }

  def createBundles(): List[Bundle] = {
    val allBundles = shipments.flatMap(potentialBundles(_))

    // for each shipment, select the route by longest length
    shipments.map(s => {
      val bundlesWithShipment = allBundles.filter(_.shipments.exists(_.id == s.id))

      bundlesWithShipment.maxBy(_.shipments.length)
    }).distinct
  }

  /**
   * Find all bundles from a node
   *
   * @param shipment the current shipment
   * @param routes   A potential route for this shipment
   * @param bundles  The already found bundles, accumulated to be tail recursive
   * @return
   */
  @tailrec
  private final def potentialBundles(
    shipment: Shipment,
    routes: List[Shipment] = Nil,
    bundles: List[Bundle] = Nil
  ): List[Bundle] = {
    val nextRoute = shipment :: routes

    val availableShipments = shipments.filter(potential =>
      potential.start == shipment.end &&
      potential.day == shipment.day.nextDay
    )

    if (availableShipments.isEmpty) {
      return List(Bundle(nextRoute.reverse)) ++ bundles
    }

    val nextShipment = availableShipments.head

    potentialBundles(nextShipment, nextRoute, bundles)
  }
}
