package guardiancouncil

import chisel3._
import chisel3.experimental.{BaseModule}
import chisel3.stage.ChiselStage


//==========================================================
// Parameters
//==========================================================
case class TE_PC_Params (
  width_core_pc: Int,
  width_te_pc: Int
)

// *Driver is used for verilog generation
object TE_PC_Driver extends App {
  val p = new TE_PC_Params (40, 64)
  (new ChiselStage).emitVerilog(new TE_PC(p), args)
}

//==========================================================
// I/Os
//==========================================================
class TE_PC_IO (params: TE_PC_Params) extends Bundle {
  val resetvector_in = Input(UInt(params.width_core_pc.W))
  val te_pcaddr_in = Input(UInt(params.width_core_pc.W))
  val te_inst_in = Input(UInt(32.W))
  val te_loadcounter_out = Output(UInt(params.width_te_pc.W))
}

trait HasTE_PC_IO extends BaseModule {
  val params: TE_PC_Params
  val io = IO(new TE_PC_IO(params))
}

//==========================================================
// Implementations
//==========================================================
class TE_PC (val params: TE_PC_Params) extends Module with HasTE_PC_IO
{

  val sys_inialised_reg = RegInit (false.B) // Register used to identify if the system is inialised
  val pcaddr_current_reg = RegInit (io.resetvector_in)
  val te_loadcounter_current_reg = RegInit (0.U(params.width_te_pc.W))

  val sys_inialised_wire = WireInit(false.B)
  val te_loadcounter_next_wire = WireInit(0.U(params.width_te_pc.W))
  val new_commit_wire = WireInit(false.B)
  val opcode_wire = WireInit(0.U(params.width_te_pc.W)) // Opcode only requires 4 bits, but we used 40 bits to avoid lint warnings
  val new_load_wire = WireInit(false.B)

  sys_inialised_wire := sys_inialised_reg
  te_loadcounter_next_wire := Mux((new_load_wire === true.B) && (sys_inialised_wire === true.B),
                                  te_loadcounter_current_reg + 1.U,
                                  te_loadcounter_current_reg)

  new_commit_wire := Mux(pcaddr_current_reg =/= io.te_pcaddr_in, true.B, false.B)
  opcode_wire := io.te_inst_in & 0x000000007F.U
  new_load_wire := Mux((opcode_wire === 0x0000000003.U) && new_commit_wire, true.B, false.B)

  // Check if the system is initalised
  when (io.te_pcaddr_in === io.resetvector_in)
  {
    sys_inialised_reg := true.B
  } .otherwise {
    sys_inialised_reg := sys_inialised_reg
  }

  // Core: PC shaddow register
  when (new_commit_wire === true.B) {
    when (sys_inialised_wire === true.B) {
      pcaddr_current_reg := io.te_pcaddr_in
    } .otherwise {
      pcaddr_current_reg := io.resetvector_in
    }
  } .otherwise {
    pcaddr_current_reg := pcaddr_current_reg
  }

  te_loadcounter_current_reg := te_loadcounter_next_wire

  io.te_loadcounter_out := te_loadcounter_next_wire

}
