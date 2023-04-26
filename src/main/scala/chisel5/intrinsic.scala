//package chisel5

//import chisel3._
//import chisel3.experimental.IntrinsicModule
//
//class ExampleIntrinsicModule(str: String) extends IntrinsicModule(
//  "OtherIntrinsic",
//  Map("STRING" -> str)) {
//  val foo = IO(new Bundle() {
//    val in = Input(UInt())
//    val out = Output(UInt(32.W))
//  })
//}
