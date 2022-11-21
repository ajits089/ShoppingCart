package uk.gov.hmrc.interview.shoppingcart

case class Pence(value: Long)

sealed trait Fruit {
  val cost: Pence
}

object Fruit {
  case object Apple extends Fruit {
    override val cost: Pence = Pence(60)
  }

  case object Orange extends Fruit {
    override val cost: Pence = Pence(25)
  }
}

object ShoppingCart {

  def checkout(cart: List[Fruit]): Pence = {
    val total = cart.foldLeft[Long](0)((total, item) => total + item.cost.value)

    Pence(total)
  }
}

