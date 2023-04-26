package arithmetics

import chisel3._

class AdderIO(width: Int) extends Bundle{
  //input
  val in = new Bundle{
    val carry = Input(Bool())
    val a = Input(UInt(width.W))
    val b = Input(UInt(width.W))
  }

  //output
  val out = new Bundle{
    val carry = Output(Bool())
    val z = Output(UInt(width.W))
  }
}

/**
 * Reference Adder
 */
class ReferenceAdder extends Module{
  val io = IO(new AdderIO(8))
  io.out := (io.in.a +& io.in.b + io.in.carry.asUInt).asTypeOf(io.out)
}

/**
 * Fast Carry
 */
class RippleCarryAdder extends Module{
  val io = IO(new AdderIO(8))
}

class CarryLookAheadAdder extends Module{
  val io = IO(new AdderIO(8))
}

class CarrySkipAdder extends Module{
  val io = IO(new AdderIO(8))
}

