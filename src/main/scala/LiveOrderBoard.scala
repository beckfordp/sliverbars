package com.silverbars

import com.silverbars.OrderType._

// case class to represent Live Orders as an immutable data structure
case class LiveOrderBoard(orders: List[Order] = Nil) extends LiverOrderBoardT {
  def register(anOrder: Order): LiveOrderBoard = {
    LiveOrderBoard(anOrder :: orders)
  }

  def cancel(anOrder: Order): LiveOrderBoard = {
    LiveOrderBoard((orders diff List(anOrder)))
  }

}

// I chose to refactor out the operations that can be performed on Live Orders into a separate Trait
trait LiverOrderBoardT {
  val orders: List[Order]


  // sell and buy are structurally similar, but I chose not to try and dry them up because:
  // 1. They are both single statements
  // 2. Whilst similar both are in fact different. E.g. buy sorts in reverse order, and returns Buy
  //    transactions rather then Sell
  def sell: List[Sell] = {
    orders.filter(_.orderType == SELL).groupBy(_.unitPrice)
      .map {
        case (unitPrice: Double, orders: List[Order]) =>  Sell(orders.map(_.quantity).sum, unitPrice)
      }.toList.sortBy(_.unitPrice)
  }

  def buy: List[Buy] = {
    orders.filter(_.orderType == BUY).groupBy(_.unitPrice)
      .map {
        case (unitPrice: Double, orders: List[Order]) =>  Buy(orders.map(_.quantity).sum, unitPrice)
      }.toList.sortBy(_.unitPrice).reverse
  }

  def all: List[Transaction] = sell ++ buy

  def show: List[String] = all.map(_.toString)

}
