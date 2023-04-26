package control

import chisel3._
import chisel3.util.BitPat
import chisel3.util.experimental.decode._

/**
 * ---
 * TruthTable Example
 */
trait SimpleTrueTable {

  val table = TruthTable(
    Map(    //input  -> output
      BitPat("b001") -> BitPat("b?1?"), 
      BitPat("b010") -> BitPat("b1??"), 
      BitPat("b100") -> BitPat("b1??"),
      BitPat("b101") -> BitPat("b1??"),
      BitPat("b111") -> BitPat("b?1?")
    ),
    BitPat("b111") //initial value??
  ) 
}

class SimpleDecoder extends Module with SimpleTrueTable {

  val input =  IO(Input(UInt(3.W)))
  val output = IO(Output(UInt(3.W)))

  output := decoder(input, table)
  // output := decoder(input, table)(chisel3.experimental.SourceInfo.materialize)
}

import chisel3.util.BitPat
import chisel3.util.experimental.decode._


//DecodeTable Example

// 입력 정의
case class Pattern(val name: String, val code: BigInt) extends DecodePattern {
  def bitPat: BitPat = BitPat("b" + code.toString(2)) 
}

// 출력 패턴 정의
object NameContainsAdd extends BoolDecodeField[Pattern] {
  def name = "name contains 'add'"
  def genTable(i: Pattern) = if (i.name.contains("add")) y else n
}

object NameContainsSub extends DecodeField[Pattern, UInt] {
  def name = "name contains 'sub'"
  def chiselType = UInt() //output type
  def genTable(i: Pattern) = if (i.name.contains("sub")) BitPat("b11?") else BitPat("b1?1") //output table
}


import chisel3._
import chisel3.util.experimental.decode._

class SimpleDecodeTable extends Module {
  val allPossibleInputs = Seq(Pattern("add", BigInt("3")), Pattern("addi", BigInt("2"))) /* can be generated */
  val decodeTable = new DecodeTable(allPossibleInputs, Seq(NameContainsAdd))
  
  val input = IO(Input(UInt(4.W)))
  val isAddType = IO(Output(Bool()))
  val decodeResult = decodeTable.decode(input)
  isAddType := decodeResult(NameContainsAdd)
}

