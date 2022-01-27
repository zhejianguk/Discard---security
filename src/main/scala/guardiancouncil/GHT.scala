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

  val sys_inialised_reg = RegInit (false.B) // Register used to identify if the system is inialised
  val core_pc_current_reg = RegInit (io.core_reset_vector_in)
  val ght_pc_counter_reg = RegInit (0.U(params.width_GHTPCCount.W))

  when (io.core_pc_in === io.core_reset_vector_in)
  {
    sys_inialised_reg := true.B
  } .otherwise {
    sys_inialised_reg := sys_inialised_reg
  }

  def val_incr1 (currnt: UInt): UInt = {
      var nxt = currnt + 1.U;
      nxt
  }

  // Core: PC shaddow register
  when ((sys_inialised_reg === true.B) && (core_pc_current_reg =/= io.core_pc_in)) {
    core_pc_current_reg := io.core_pc_in
  } .elsewhen ((sys_inialised_reg === false.B) && (core_pc_current_reg =/= io.core_reset_vector_in)) {
    core_pc_current_reg := io.core_reset_vector_in
  }
  .otherwise {
    core_pc_current_reg := core_pc_current_reg
  }

  // GHT: PC counter
  when ((sys_inialised_reg === true.B) && (core_pc_current_reg =/= io.core_pc_in)) {
    ght_pc_counter_reg := this.val_incr1(ght_pc_counter_reg)
  } .otherwise {
    ght_pc_counter_reg := ght_pc_counter_reg
  }

  io.ght_pc_count_out := ght_pc_counter_reg
}
