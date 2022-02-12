package guardiancouncil


import chisel3._
import chisel3.experimental.{BaseModule}
import chisel3.stage.ChiselStage

//==========================================================
// Parameters
//==========================================================
case class GHTParams(
  width_core_pc: Int,
  width_te_pc: Int
)

// *Driver is used for verilog generation
object GHT_Driver extends App {
  val p = new GHTParams (40, 64)
  (new ChiselStage).emitVerilog(new GHT(p), args)
}

//==========================================================
// I/Os
//==========================================================
trait HasGHT_IO extends BaseModule {
  val params: GHTParams
  val params_te_pc = new TE_PC_Params (params.width_core_pc, params.width_te_pc)

  val io_te_pc = IO(new TE_PC_IO(params_te_pc))
}


class GHT (val params: GHTParams) extends Module with HasGHT_IO
{
  val u_te_pc = Module (new TE_PC(TE_PC_Params (params.width_core_pc, params.width_te_pc)))

  u_te_pc.io <> io_te_pc

}
