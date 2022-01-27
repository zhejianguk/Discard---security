package guardiancouncil


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage


case class GHMParams(
  width_GHTPCCount: Int
)

// *Driver is used for verilog generation
object GHM_Driver extends App {
  val p = new GHMParams (32)
  (new ChiselStage).emitVerilog(new GHM(p), args)
}

class GHM (params: GHMParams) extends Module {
    val io = IO(new Bundle {
    val ghm_in = Input(UInt(params.width_GHTPCCount.W))
    val ghm_out = Output(UInt(32.W))
  })

  val ghm_out_reg = RegInit (0.U(params.width_GHTPCCount.W))

  // Below code are useless, but just to simulate the function of GHM
  val issca = io.ghm_in

  when (issca === 0x0FFFFFFF.U){
    ghm_out_reg := issca + 1.U
  } .otherwise {
    ghm_out_reg := ghm_out_reg
  }
  // End of useless code

  io.ghm_out := ghm_out_reg
}
