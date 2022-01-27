package guardiancouncil


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage


case class GHTParams(
  width_corePC: Int,
  width_GHTPCCount: Int
)

// *Driver is used for verilog generation
object GHT_Driver extends App {
  val p = new GHTParams (40, 64)
  (new ChiselStage).emitVerilog(new GHT(p), args)
}


class GHT (params: GHTParams) extends Module {
    val io = IO(new Bundle {
    val core_reset_vector_in = Input(UInt(params.width_corePC.W))
    val core_pc_in = Input(UInt(params.width_corePC.W))
    val ght_pc_count_out = Output(UInt(params.width_GHTPCCount.W))
  })

  val core_pc_current_reg = RegInit (io.core_reset_vector_in)
  val ght_pc_counter_reg = RegInit (0.U(params.width_GHTPCCount.W))

  def val_incr1 (currnt: UInt): UInt = {
      var nxt = currnt + 1.U;
      nxt
  }

  // Core: PC shaddow register
  when (core_pc_current_reg =/= io.core_pc_in) {
    core_pc_current_reg := io.core_pc_in
  } .otherwise {
    core_pc_current_reg := core_pc_current_reg
  }

  // GHT: PC counter
  when (core_pc_current_reg =/= io.core_pc_in) {
    ght_pc_counter_reg := this.val_incr1(ght_pc_counter_reg)
  } .otherwise {
    ght_pc_counter_reg := ght_pc_counter_reg
  }

  io.ght_pc_count_out := ght_pc_counter_reg
}
