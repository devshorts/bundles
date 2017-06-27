package com.onoffwitch.convoy

import org.scalatest._

class BundleTests extends FlatSpec with Matchers {
  "Shipments" should "be grouped into bundles" in {
    val input =
      """1 SEATTLE PORTLAND M
        |2 PORTLAND SAN_FRANCISCO T
        |3 PORTLAND DENVER T
        |22 SEATTLE DENVER R
        |44 DENVER SEATTLE W
        |99 DENVER KANSAS_CITY F""".stripMargin

    getBundles(input) should contain allElementsOf List(
      "3 44 22 99", "1 2"
    )
  }

  it should "work when there are no routes" in {
    val input =
      """1 SEATTLE FOO M
        |2 PORTLAND SAN_FRANCISCO T
        |3 PORTLAND BAR T
        |22 SEATTLE XX R
        |44 BIZ BAZ W
        |99 DENVER KANSAS_CITY F""".stripMargin

    getBundles(input) should contain allElementsOf List(
      "1", "2", "3", "44", "22", "99"
    )
  }

  it should "verify invalid input when empty" in {
    assertThrows[InvalidShipmentInput] {
      getBundles("")
    }
  }

  it should "verify invalid input when malformed" in {
    assertThrows[InvalidShipmentInput] {
      getBundles("1 SEATTLE FOO X")
    }
  }

  def getBundles(input: String): List[String] = {
    new ShipmentResolver(Parser(input)).createBundles().map(_.toString)
  }
}
