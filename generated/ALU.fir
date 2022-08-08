circuit ALU :
  module ALU :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip a : UInt<8>, flip b : UInt<8>, c : UInt<8>}

    reg reg : UInt<8>, clock with :
      reset => (reset, UInt<8>("h0")) @[Add.scala 39:20]
    node _reg_T = add(io.a, io.b) @[Add.scala 40:15]
    node _reg_T_1 = tail(_reg_T, 1) @[Add.scala 40:15]
    reg <= _reg_T_1 @[Add.scala 40:7]
    io.c <= reg @[Add.scala 42:8]

