package arithmetics.tester

import chisel3._
import chiseltest._
import chiseltest.ChiselScalatestTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import arithmetics._

trait AddBehavior {
  this: AnyFlatSpec with ChiselScalatestTester =>

  def testAddition(a: Int, b: Int): Unit = {
    val result = (a + b)
    it should s"$a + $b == $result" in {
      test(new ReferenceAdder) { c =>
        c.io.in.carry.poke(false.B)
        c.io.in.a.poke(a.U(8.W))
        c.io.in.b.poke(b.U(8.W))
        c.clock.step()
        c.io.out.z.expect(result.U(8.W))
      }
    }
  }

  def testAdditionWithCarryInput(a: Int, b: Int): Unit = {
    val result = (a + b + 1)
    it should s"$a + $b + 1 == $result" in {
      test(new ReferenceAdder) { c =>
        c.io.in.carry.poke(true.B)
        c.io.in.a.poke(a.U(8.W))
        c.io.in.b.poke(b.U(8.W))
        c.clock.step()
        c.io.out.z.expect(result.U(8.W))
      }
    }
  }
}


class AdderTest extends AnyFlatSpec with AddBehavior with ChiselScalatestTester with Matchers {
  behavior of "Adder"
  val testVector: List[(Int, Int)] = List[(Int, Int)](
    (1, 2),
    (3, 4),
    (4, 5),
    (5, 2),
    (7, 8),
  )
  testVector.foreach { data =>
    it should behave like testAddition(data._1, data._2)
    it should behave like testAdditionWithCarryInput(data._1, data._2)
  }
}

