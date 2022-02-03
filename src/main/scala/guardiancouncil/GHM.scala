package guardiancouncil


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage
import chisel3.experimental.{IntParam, BaseModule}

case class GHMParams(
  width_GHTPCCount: Int,
  number_of_rockets: Int
)


class GHMIO(params: GHMParams) extends Bundle {
  // val ghm_in = Input(UInt(params.width_GHTPCCount.W))
  val ghm_ins = Input(Vec(params.number_of_rockets, UInt(32.W)))
  val ghm_out = Output(UInt(params.width_GHTPCCount.W))
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
  val p = new GHMParams (32, 2)
  (new ChiselStage).emitVerilog(new GHM(p), args)
}

class GHM (val params: GHMParams) extends Module with HasGHMIO
{
  val ghm_in_regs = RegInit(VecInit(Seq.fill(params.number_of_rockets)(0.U(32.W))))

  val ghm_out_reg = RegInit (0.U(params.width_GHTPCCount.W))

  for(i <- 0 to params.number_of_rockets - 1) {
    ghm_in_regs(i) := io.ghm_ins(i)
  }



  // Below code are useless, but just to simulate the function of GHM
  var issca = 0.U

  when (issca =/= 0x0FFFFFFF.U){
    ghm_out_reg := issca + 1.U + ghm_in_regs(0) - ghm_in_regs(1)
  } .otherwise {
    ghm_out_reg := issca + 0.U + ghm_in_regs(0) - ghm_in_regs(1)
  }
  // End of useless code

  io.ghm_out := ghm_out_reg
}
