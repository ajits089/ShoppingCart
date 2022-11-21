package uk.gov.hmrc.interview.shoppingcart

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import uk.gov.hmrc.interview.shoppingcart.Fruit.{Apple, Orange}
import uk.gov.hmrc.interview.shoppingcart.ShoppingCart.checkout

class ShoppingCartSpec extends AnyFlatSpec {

  behavior of "ShoppingCart Checkout"

  //Step 1 tests with no discounts

  it should "return correct total when the cart is empty" in {
    checkout(List.empty) shouldBe Pence(0)
  }

  it should "return correct total when there is only one item" in {
    checkout(List(Apple)) shouldBe Pence(60)
    checkout(List(Orange)) shouldBe Pence(25)
  }

  it should "return correct total when there are items of just one type" in {
    checkout(List(Apple, Apple)) shouldBe Pence(120)
    checkout(List(Apple, Apple, Apple)) shouldBe Pence(180)
    checkout(List(Orange, Orange)) shouldBe Pence(50)
    checkout(List(Orange, Orange, Orange)) shouldBe Pence(75)
  }

  it should "return correct total when the cart contains different item types" in {
    checkout(List(Apple, Orange)) shouldBe Pence(85)
    checkout(List(Apple, Orange, Apple, Orange)) shouldBe Pence(170)
    checkout(List(Apple, Apple, Orange, Apple)) shouldBe Pence(205)
  }
}
