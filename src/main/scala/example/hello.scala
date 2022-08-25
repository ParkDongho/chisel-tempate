package example

import chisel3._
import chisel3.util._

 /**
   *
   * Provides main classes of the bar API.
   *
   * ==Overview==
   * hello chisel
   *
   * <img src="diagram-foo.svg" />
   *
   * ==Ports==
   *
   *  - inputs
   *    - io.A
   *    - io.B  
   *    - io.Sel  
   *  - outpus
   *    - io.Z
   *
   * <img src="timing_diagram_foo.svg" />
   *
   */
class Hello extends Module { 
  val io = IO(new Bundle {
    val led = Output(UInt(1.W)) 
  })
  val CNT_MAX = (50000000 / 2 - 1).U 
  
  val cntReg = RegInit(0.U(32.W))
  val blkReg = RegInit(0.U(1.W))
  
  cntReg := cntReg + 1.U
  when(cntReg === CNT_MAX) {
    cntReg := 0.U
    blkReg := ~blkReg 
  }
  io.led := blkReg 
}                                                                                       
object HelloMain extends App {
  emitVerilog(new Hello(), Array("--target-dir", "generated"))
}
