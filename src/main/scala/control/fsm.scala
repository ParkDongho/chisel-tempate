package control

import chisel3._
import chisel3.util.{switch, is}

object DetectTwoOnes {
  object State extends ChiselEnum {
    val sNone = Value(1.U)
    val sOne1 = Value(2.U)
    val sTwo1s = Value(4.U)
  }
}

/* This FSM detects two 1's one after the other */
class FsmExample extends Module {
  import DetectTwoOnes.State
  import DetectTwoOnes.State._

  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(Bool())
    val state = Output(State())
  })

  val state = RegInit(sNone)

  io.out := (state === sTwo1s)
  io.state := state

  switch (state) {
    is (sNone) {
      when (io.in) {
        state := sOne1
      }
    }
    is (sOne1) {
      when (io.in) {
        state := sTwo1s
      } .otherwise {
        state := sNone
      }
    }
    is (sTwo1s) {
      when (!io.in) {
        state := sNone
      }
    }
  }
}

