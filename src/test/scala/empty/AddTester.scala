/*
 * Dummy tester to start a Chisel project.
 *
 * Author: Martin Schoeberl (martin@jopdesign.com)
 * 
 */

package example

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HelloTest extends AnyFlatSpec with ChiselScalatestTester {
  "Add" should "pass" in {
    test(new Hello) { dut =>
      for (a <- 0 to 2) {
        for (b <- 0 to 3) {
          val result = a + b
          dut.clock.step(1)
        }
      }
    }
  }
}
