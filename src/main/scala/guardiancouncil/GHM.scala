package guardiancouncil


import chisel3._
import chisel3.util._
import chisel3.stage.ChiselStage
import chisel3.experimental.{IntParam, BaseModule}

case class GHMParams(
  number_of_rockets: Int,
  width_GH_packet: Int // Default: 202: 3: func; 7: opcode; 64 D, 64 S, 64 S.
)


class GHMIO(params: GHMParams) extends Bundle {
  // val ghm_in = Input(UInt(params.width_GHTPCCount.W))
  // val ghm_packet_ins = Input(Vec(params.number_of_rockets, UInt(32.W)))
  // val ghm_packet_out = Output(UInt(params.width_GHTPCCount.W))
  val ghm_packet_in   = Input(UInt(params.width_GH_packet.W))
  val ghm_packet_outs = Output(Vec(params.number_of_rockets, UInt(params.width_GH_packet.W)))
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
  val p = new GHMParams (3, 205)
  (new ChiselStage).emitVerilog(new GHM(p), args)
}

class GHM (val params: GHMParams) extends Module with HasGHMIO
{
  val xLen                      = 8 // Delete it in Chipyard
  val packet_in_reg             = RegInit (0.U(params.width_GH_packet.W))
  val packet_out_wires          = WireInit (VecInit(Seq.fill(params.number_of_rockets)(0.U(params.width_GH_packet.W))))

  val func                      = packet_in_reg(3*xLen+9, 3*xLen+7)
  val opcode                    = packet_in_reg(3*xLen+6, 3*xLen)
  val big_core_rd               = packet_in_reg(3*xLen-1, 2*xLen)
  val big_core_rs1              = packet_in_reg(2*xLen-1, xLen)
  val big_core_rs2              = packet_in_reg(xLen-1,   0)
  val little_core_dest          = WireInit(0.U(log2Up(params.number_of_rockets).W))
  val new_packet                = WireInit(false.B)


  val u_ghm_sch                 = Module (new GHM_SCH(GHM_SCH_Params (params.number_of_rockets)))
  u_ghm_sch.io.big_core_func   := func
  u_ghm_sch.io.big_core_opcode := opcode
  little_core_dest             := u_ghm_sch.io.dest_little_core_id
  new_packet                   := Mux(Cat(func, opcode) =/= 0.U, true.B, false.B)

  when (new_packet === true.B) {
    packet_out_wires(little_core_dest) := packet_in_reg
  }

  packet_in_reg                := io.ghm_packet_in

  for(i <- 0 to params.number_of_rockets - 1) {
    io.ghm_packet_outs(i)      := packet_out_wires(i)
  }
}
