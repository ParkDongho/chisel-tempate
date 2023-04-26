package aritmetics

import chisel3._
import chisel3.util._

object AluMux1Sel extends ChiselEnum {
  val ADD, SUB, INC, DEC = Value
}

import AluMux1Sel._

class Counter(WIDTH: Int) {
  val value = Reg(UInt(WIDTH.W))
  val aluSel = Wire(AluMux1Sel())

  val lhs     = Wire(UInt(WIDTH.W))
  val rhs     = Wire(UInt(WIDTH.W))
  val carryIn = Wire(Bool())
  rhs := 0.U
  lhs := 0.U
  carryIn := 0.B

  val strideWire = Wire(UInt(WIDTH.W))
  strideWire := 0.U

  value := lhs + rhs + carryIn
  lhs := value

  switch (aluSel) {
    is (ADD) {
      rhs := strideWire
      carryIn := 0.B
    }
    is (SUB) {
      rhs := strideWire
      carryIn := 0.B
    }
    is (INC) {
      rhs := 0.U
      carryIn := 1.B
    }
    is (DEC) {
      rhs := (-1.S(WIDTH.W)).asUInt
      carryIn := 0.B
    }
  }

  // Control
  def inc() : Unit = {
    aluSel := INC
  }
  def dec() : Unit = {
    aluSel := DEC
  }
  def inc(stride: UInt) : Unit = {
    aluSel := ADD
    strideWire := stride
  }
  def dec(stride: UInt) : Unit = {
    aluSel := SUB
    strideWire := stride
  }

  // Status
  def isZero() : Bool = {
    value.andR
  }

}

class CounterMain(WIDTH: Int) extends Module{
  val out = IO(Output(UInt(WIDTH.W)))
  val sel = IO(Input(Bool()))

  val count = new Counter(8)

  out := count.value

  when(sel){
    count.inc()
  } .otherwise{
    count.dec(2.U)
  }
}

/////////////////////////////////////
// DataView & DataProduct Use Case //
/////////////////////////////////////
import chisel3.util.Valid
import chisel3.experimental.dataview._

// Loosely based on chisel3.util.Counter
class MyCounter(val width: Int) {
  /** Indicates if the Counter is incrementing this cycle */
  val active = WireDefault(false.B)
  val value = RegInit(0.U(width.W))
  def inc(): Unit = {
    active := true.B
    value := value + 1.U
  }
  def reset(): Unit = {
    value := 0.U
  }
}


object MyCounterImpl{
  implicit val counterProduct = new DataProduct[MyCounter] {
    // The String part of the tuple is a String path to the object to help in debugging
    def dataIterator(a: MyCounter, path: String): Iterator[(Data, String)] =
      List(a.value -> s"$path.value", a.active -> s"$path.active").iterator
  }
  // Now this works
  implicit val counterView = DataView[MyCounter, Valid[UInt]](c => Valid(UInt(c.width.W)), _.value -> _.bits, _.active -> _.valid)
}

