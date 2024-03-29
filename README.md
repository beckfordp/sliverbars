A Scala Implementation of the Silver Bars Exercise
==================================================

I chose Scala because it is the language that I've been using the most recently... 

I also chose to do it in a functional style, which of course Scala lends itself to. 
The same could be achieved in Java 8 also.

Another design choice of mine was to do the simplest thing that could possibly work.

Which means writing the minimal amount of code needed to pass the tests. The
code works, is clean (in my opinion) and passes the tests, derived from the specification.

It does have some design aspects that I feel would probably change in a real world application:

1. Unit of measure is assumed to be fixed, and hardcoded as kg
2. Unit Price is assumed to be in a fixed currency, pounds sterling

It is likely that in a real world production system these simplifications wouldn't remain. As 
the current requirements stand however, the code as presented is sufficient.

This is how I prefer to grow software. To keep it barely sufficient and only add more complexity when
required to do so by the requirements.

That's the overarching design philosophy.

####To run the tests

1. Install sbt
2. cd to the root directory and run: `sbt test`


The Exercise
============

Interview exercise
Imagine you're working as a programmer for a company called Silver Bars Marketplace and you have just received a new requirement. In it we would like to display to our users how much demand for silver bars there is on the market.
To do this we would like to have a 'Live Order Board' that can provide us with the following functionality:
1) Register an order. Order must contain these fields:
- user id
- order quantity (e.g.: 3.5 kg)
- price per kg (e.g.: £303)
- order type: BUY or SELL
2) Cancel a registered order - this will remove the order from 'Live Order Board'
3) Get summary information of live orders (see explanation below)
Imagine we have received the following orders:
- a) SELL: 3.5 kg for £306 [user1]
- b) SELL: 1.2 kg for £310 [user2]
- c) SELL: 1.5 kg for £307 [user3]
- d) SELL: 2.0 kg for £306 [user4]
Our ‘Live Order Board’ should provide us the following summary information:
- 5.5 kg for £306 // order a + order d
- 1.5 kg for £307 // order c
- 1.2 kg for £310 // order b
The first thing to note here is that orders for the same price should be merged together (even when they are from different users). In this case it can be seen that order a) and d) were for the same amount (£306) and this is why only their sum (5.5 kg) is displayed (for £306) and not the individual orders (3.5 kg and 2.0 kg).The last thing to note is that for SELL orders the orders with lowest prices are displayed first. Opposite is true for the BUY orders.
Please provide the implementation of the live order board which will be packaged and shipped as a library to be used by the UI team. No database or UI/WEB is needed for this assignment (we're absolutely fine with in memory solution). The only important thing is that you just write it according to your normal standards.
NOTE: if during your implementation you'll find that something could be designed in multiple different ways, just implement the one which seems most reasonable to you and if you could provide a short (once sentence) reasoning why you choose this way and not another one, it would be great.