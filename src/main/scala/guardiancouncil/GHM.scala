package guardiancouncil


import chisel3._
import chisel3.stage.ChiselStage
import chisel3.experimental.{IntParam, BaseModule}



case class GHMParams(
  width_GHTPCCount: Int
)


class GHMIO(params: GHMParams) extends Bundle {
  // val ghm_in = Input(UInt(params.width_GHTPCCount.W))
  val ghm_out = Output(UInt(32.W))
}

trait HasGHMIO extends BaseModule {
  val params: GHMParams
  val io = IO(new GHMIO(params))
}

//==========================================================
// Implementations
//==========================================================
// *Driver is used for verilog generation
object GHM_Driver extends App {
  val p = new GHMParams (32)
  (new ChiselStage).emitVerilog(new GHM(p), args)
}

class GHM (val params: GHMParams) extends Module with HasGHMIO
{
  val ghm_out_reg = RegInit (0.U(params.width_GHTPCCount.W))

  // Below code are useless, but just to simulate the function of GHM
  val issca = ghm_out_reg

  when (issca =/= 0x0FFFFFFF.U){
    ghm_out_reg := issca + 1.U
  } .otherwise {
    ghm_out_reg := 0x55.U
  }
  // End of useless code

  io.ghm_out := ghm_out_reg
}
