package arithmetics

import chisel3._
import chisel3.util._

/** taken from https://github.com/schoeberl/chisel-examples/blob/master/src/main/scala/simple/Alu.scala */
class Alu(size: Int) extends Module {
  val io = IO(new Bundle {
    val fn = Input(UInt(2.W))
    val a = Input(UInt(size.W))
    val b = Input(UInt(size.W))
    val result = Output(UInt(size.W))
  })

  val result = Wire(UInt(size.W))
  result := 0.U

  switch(io.fn) {
    is(0.U) { result := io.a + io.b }
    is(1.U) { result := io.a - io.b }
    is(2.U) { result := io.a | io.b }
    is(3.U) { result := io.a & io.b }
  }
  io.result := result
}

