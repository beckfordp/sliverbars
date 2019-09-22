package com.silverbars

import com.silverbars.OrderType.OrderType


object OrderType extends Enumeration {
  type OrderType = Value
  val BUY, SELL = Value
}

sealed trait Transaction {
  val quantity: Double
  val unitPrice: Double
  val orderType: OrderType

  override def toString: String = f"$orderType: $quantity kg for £$unitPrice%.0f"
}

case class Sell(quantity: Double, unitPrice: Double, orderType: OrderType = OrderType.SELL) extends Transaction
case class Buy(quantity: Double, unitPrice: Double, orderType: OrderType = OrderType.BUY) extends Transaction

case class Order(userId: String, quantity: Double, unitPrice: Double, orderType: OrderType) {
  override def toString: String = f"$orderType: $quantity kg for £$unitPrice%.0f"

}


