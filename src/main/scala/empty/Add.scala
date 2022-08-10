/*
 * Dummy file to start a Chisel project.
 *
 * 
 */

package empty

import chisel3._
import chisel3.util._

/**
 *
 * Provides main classes of the bar API.
 *
 * ==Overview==
 * ALU module are depicted on the following diagram:  
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
class ALU extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val c = Output(UInt(8.W))
  })
  

  val reg = RegInit(0.U(8.W))
  reg := io.a + io.b

  io.c := reg
}

object ALUMain extends App {
  println("Generating the adder hardware")
  emitVerilog(new ALU(), Array("--target-dir", "generated"))
}

