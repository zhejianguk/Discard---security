package programcounter


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage


// *Driver is used for verilog generation
object CounterDriver extends App {
  (new ChiselStage).emitVerilog(new Counter(32),  Array(""))
}


class Counter(CounterWidth: Int) extends Module {
    require(CounterWidth >= 0)

    val io = IO(new Bundle {
    val trigger = Input(Bool())
    val counter_out = Output(UInt(CounterWidth.W))
  })

    val counter_value_reg = RegInit (0.U(CounterWidth.W))

    def val_incr (counter_currnt: UInt): UInt = {
        var counter_nxt = counter_currnt + 1.U;
        counter_nxt
    }

    when (io.trigger === true.B) {
        counter_value_reg := val_incr(counter_value_reg)
    }.otherwise {
        counter_value_reg := counter_value_reg
    }

    io.counter_out := counter_value_reg
}
