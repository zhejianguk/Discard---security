package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_TE_PC extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TE_PC"
  it should "Test guardiancouncil.TE_PC" in {
    val p = new TE_PC_Params (40, 64)
    test(new TE_PC(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

    //==========================================================
    // initialisation
    //==========================================================
    dut.io.resetvector_in.poke(0x10040.U)
    dut.io.te_pcaddr_in.poke(0.U)
    dut.io.te_pcaddr_in.poke(0.U)
    dut.io.te_inst_in.poke(0.U)
    dut.io.te_loadcounter_out.expect(0.U)
    dut.clock.step(7) // clk+10

    //======== Commit 1 instruction before system initialisation
    dut.io.te_pcaddr_in.poke(0x10044.U)
    dut.io.te_inst_in.poke(0xAAAA03.U)
    dut.clock.step(1) // clk+1
    dut.io.te_loadcounter_out.expect(0.U)
    dut.clock.step(1) // clk+1

    //======== inialised the system
    dut.io.te_pcaddr_in.poke(0x10040.U)
    dut.io.te_inst_in.poke(0xAAAA00.U)
    dut.io.te_loadcounter_out.expect(0.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x10044.U)
    dut.io.te_inst_in.poke(0xAAAA01.U)
    dut.io.te_loadcounter_out.expect(0.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x10048.U)
    dut.io.te_inst_in.poke(0xAAAA02.U)
    dut.io.te_loadcounter_out.expect(0.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x1004C.U)
    dut.io.te_inst_in.poke(0xAAAA03.U)
    dut.io.te_loadcounter_out.expect(1.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x10050.U)
    dut.io.te_inst_in.poke(0xBFB303.U)
    dut.io.te_loadcounter_out.expect(2.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x10054.U)
    dut.io.te_inst_in.poke(0xBFB309.U)
    dut.io.te_loadcounter_out.expect(2.U)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.te_pcaddr_in.poke(0x10058.U)
    dut.io.te_inst_in.poke(0x55AA03.U)
    dut.io.te_loadcounter_out.expect(3.U)
    dut.clock.step(1) // clk+1

    }
  }
  it should "All tested!"
}
