package com.silverbars

import com.silverbars.OrderType._
import org.scalatest.{FlatSpec, Matchers}

class LiveOrderBoardSpec extends FlatSpec with Matchers {

   behavior of "Live Order Board"

   it should "register orders" in {
     val liveOrders = LiveOrderBoard()
       .register(Order("user1", 3.5, 303.00, SELL))
       .register(Order("user2", 3.5, 303.00, BUY))

     liveOrders.orders should be (
       List(Order("user2", 3.5, 303.00, BUY),
            Order("user1", 3.5, 303.00, SELL)))
   }

  it should "cancel orders" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("user1", 3.5, 303.00, SELL))
      .register(Order("user1", 3.5, 303.00, SELL))
      .register(Order("user1", 3.5, 303.00, BUY))
      .cancel(Order("user1", 3.5, 303.00, SELL))

    liveOrders.orders should be (
      List(Order("user1", 3.5, 303.00, BUY),
           Order("user1", 3.5, 303.00, SELL)))
  }

  it should "group orders by price" in {
    LiveOrderBoard()
      .register(Order("userId", 3.5, 306, SELL))
      .register(Order("userId", 2.0, 306, SELL))
      .sell should be (List(Sell(5.5, 306)))
  }

  it should "group sell and buy orders" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("userId", 3.5, 306, SELL))
      .register(Order("userId", 3.5, 306, BUY))
      .register(Order("userId", 2.0, 306, SELL))
      .register(Order("userId", 2.0, 306, BUY))

      liveOrders.sell should be (List(Sell(5.5, 306)))
      liveOrders.buy should be (List(Buy(5.5, 306)))
  }

  it should "sort sell orders lowest price first" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("user1", 3.5, 303, SELL))
      .register(Order("user1", 3.5, 300, SELL))
      .register(Order("user1", 3.5, 310, SELL))

    liveOrders.sell should be(List(Sell(3.5, 300), Sell(3.5, 303), Sell(3.5, 310)))
  }

  it should "sort buy orders highest price first" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("user1", 3.5, 303, BUY))
      .register(Order("user1", 3.5, 300, BUY))
      .register(Order("user1", 3.5, 310, BUY))

    liveOrders.buy should be(List(Buy(3.5, 310), Buy(3.5, 303), Buy(3.5, 300)))
  }

  // I will assume that they want to list sell orders before buy, a question for my product owner?
  it should "list sell orders before buys orders" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("userId", 3.5, 306, SELL))
      .register(Order("userId", 3.5, 306, BUY))
      .register(Order("userId", 2.0, 306, SELL))
      .register(Order("userId", 2.0, 306, BUY))

    liveOrders.all should be (List(Sell(5.5, 306), Buy(5.5, 306)))
  }

  // I will assume that the units are always kg and the price is in pounds sterling to the nearest pound
  // The exercise sheet example doesn't show whether it is a sell or a buy, I've chosen to add that.
  it should "show summary information in the required format" in {
    val liveOrders = LiveOrderBoard()
      .register(Order("user1", 3.5, 306, SELL))
      .register(Order("user2", 1.2, 310, SELL))
      .register(Order("user3", 1.5, 307, SELL))
      .register(Order("user4", 2.0, 306, SELL))

    liveOrders.show should be (
    List(
      "SELL: 5.5 kg for £306",
      "SELL: 1.5 kg for £307",
      "SELL: 1.2 kg for £310"
    ))
  }
}
