package uk.gov.hmrc.interview.shoppingcart

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import uk.gov.hmrc.interview.shoppingcart.Fruit.{Apple, Orange}
import uk.gov.hmrc.interview.shoppingcart.Offer.{AppleBuyOneGetOne, Orange3For2}
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

  // Step 2 tests with discounts

  it should "return correct total when there are only apple discounts" in {
    val offers = List(AppleBuyOneGetOne)
    checkout(List.fill(1)(Apple), offers) shouldBe Pence(60)
    checkout(List.fill(2)(Apple), offers) shouldBe Pence(60)
    checkout(List.fill(3)(Apple), offers) shouldBe Pence(120)
    checkout(List.fill(4)(Apple), offers) shouldBe Pence(120)
    checkout(List.fill(5)(Apple), offers) shouldBe Pence(180)
    checkout(List.fill(10)(Apple), offers) shouldBe Pence(300)

    checkout(List.fill(3)(Apple) ++ List(Orange), offers) shouldBe Pence(145)
  }

  it should "return correct total when there are only orange discounts" in {
    val offers = List(Orange3For2)
    checkout(List.fill(1)(Orange), offers) shouldBe Pence(25)
    checkout(List.fill(2)(Orange), offers) shouldBe Pence(50)
    checkout(List.fill(3)(Orange), offers) shouldBe Pence(50)
    checkout(List.fill(4)(Orange), offers) shouldBe Pence(75)
    checkout(List.fill(5)(Orange), offers) shouldBe Pence(100)
    checkout(List.fill(7)(Orange), offers) shouldBe Pence(125)

    checkout(List.fill(5)(Orange) ++ List(Apple), offers) shouldBe Pence(160)
  }

  it should "return correct total when there are discounts on both apples and oranges" in {
    val offers = List(AppleBuyOneGetOne, Orange3For2)
    checkout(List.fill(2)(Orange) ++ List.fill(2)(Apple), offers) shouldBe Pence(110)
    checkout(List.fill(3)(Orange) ++ List.fill(3)(Apple), offers) shouldBe Pence(170)
    checkout(List.fill(5)(Orange) ++ List.fill(3)(Apple), offers) shouldBe Pence(220)
    checkout(List.fill(7)(Orange) ++ List.fill(7)(Apple), offers) shouldBe Pence(365)
  }

  it should "return 0 when the cart is empty but there are some discounts" in {
    val offers = List(AppleBuyOneGetOne, Orange3For2)
    checkout(List.empty, offers) shouldBe Pence(0)
  }
}
