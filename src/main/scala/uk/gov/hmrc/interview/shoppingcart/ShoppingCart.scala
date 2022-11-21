package uk.gov.hmrc.interview.shoppingcart

import uk.gov.hmrc.interview.shoppingcart.Fruit.{Apple, Orange}
import uk.gov.hmrc.interview.shoppingcart.Offer.{AppleBuyOneGetOne, Orange3For2}

case class Pence(value: Long)

sealed trait Fruit {
  val cost:Pence
}

object Fruit {
  case object Apple extends Fruit {
    override val cost: Pence = Pence(60)
  }
  case object Orange extends Fruit {
    override val cost: Pence = Pence(25)
  }
}

sealed trait Offer

object Offer {
  case object AppleBuyOneGetOne extends Offer
  case object Orange3For2 extends Offer
}

object ShoppingCart {

  def checkout(cart: List[Fruit], offers: List[Offer] = List.empty): Pence = {
    val total = cart.foldLeft[Long](0)((total, item) => total + item.cost.value)

    val discount = offers.map {
      case AppleBuyOneGetOne => appleDiscount(cart)
      case Orange3For2 => orangeDiscount(cart)
      case _ => 0
    }.sum

    Pence(total-discount)
  }

  private def appleDiscount(cart: List[Fruit]): Long = {
    val numberOfDiscountedApples = cart.count(_ == Apple) / 2
    Apple.cost.value * numberOfDiscountedApples
  }

  private def orangeDiscount(cart:List[Fruit]): Long = {
    val numberOfDiscountedOranges = cart.count(_ == Orange) / 3
    Orange.cost.value * numberOfDiscountedOranges
  }
}
