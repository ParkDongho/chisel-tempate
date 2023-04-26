package dut

import chisel3._
import chisel3.stage.ChiselGeneratorAnnotation
import circt.stage.{ChiselStage, FirtoolOption}
import arithmetics._

class DUT extends Module{
  val m = Module(new ReferenceAdder)
  val io = IO(new AdderIO(8))
  m.io <> io
}

object Main extends App {

  //println("====CHIRRTL====")
  //println(ChiselStage.emitCHIRRTL(new DUT(8)) )
  //println("====FIRRTL====")
  //println(
  //  ChiselStage.emitFIRRTLDialect(
  //    gen = new DUT(8),
  //    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  //  )
  //)
  //println("====System Veriloig====")
  //println(
  //  ChiselStage.emitSystemVerilog(
  //    gen = new DUT(8),
  //    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  //  )
  //)

  println("====Generate .sv Files====")
  (new ChiselStage).execute(
    //Array("--target", "chirrtl"),
    //Array("--target", "firrtl"),
    Array("--target", "systemverilog"),
    Seq(ChiselGeneratorAnnotation(() => new DUT),
      FirtoolOption("--disable-all-randomization"),
      FirtoolOption("--strip-debug-info"), 
    )
  )
}

