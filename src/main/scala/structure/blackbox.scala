package structure

import chisel3._
import chisel3.experimental._ // To enable experimental features

/////////////////////////
//                     //
/////////////////////////
class BlackBoxExample extends BlackBox(Map("DIFF_TERM" -> "TRUE",
                                  "IOSTANDARD" -> "DEFAULT")) {
  val io = IO(new Bundle {
    val O = Output(Clock())
    val I = Input(Clock())
    val IB = Input(Clock())
  })
}

class Top extends Module {
  val io = IO(new Bundle {})
  val ibufds = Module(new BlackBoxExample)
  // connecting one of IBUFDS's input clock ports to Top's clock signal
  ibufds.io.I := clock
}


////////////////////////////
//                        //
////////////////////////////
class BlackBoxRealAdd extends BlackBox {
  val io = IO(new Bundle {
    val in1 = Input(UInt(64.W))
    val in2 = Input(UInt(64.W))
    val out = Output(UInt(64.W))
  })
}


//////////////////////////////
// ExtModule                //
// Example with Xilinx differential buffer IBUFDS
class ExtModuleExample extends ExtModule(Map("DIFF_TERM"  -> "TRUE", // Verilog parameters
                                             "IOSTANDARD" -> "DEFAULT"
                     )) {
  val O  = IO(Output(Clock()))
  val I  = IO(Input(Clock()))
  val IB = IO(Input(Clock()))
}

