package mymodule


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage

import chisel3.stage.ChiselStage

object MyModuleDriver extends App {
  (new ChiselStage).emitVerilog(new MyModule, args)
}


class MyModule extends Module {
    val io = IO(new Bundle {
        val in = Input(UInt(16.W))
        val out = Output(UInt(16.W))
    })

    io.out := RegNext(io.in)
}
