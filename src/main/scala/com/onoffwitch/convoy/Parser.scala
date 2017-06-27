package com.onoffwitch.convoy

/**
 * Create shipments from input string
 */
object Parser {
  def apply(input: String): List[Shipment] = {
    input.split(System.lineSeparator()).map(Shipment(_)).toList
  }
}
