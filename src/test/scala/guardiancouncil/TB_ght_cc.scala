package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_ght_cc extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TB_ght_cc"
  it should "Test guardiancouncil.TB_ght_cc" in {
    val p = new GHT_CC_Params (40)
    test(new GHT_CC(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

    //==========================================================
    // initialisation
    //==========================================================
    dut.io.resetvector_in.poke(0x10040.U)
    dut.io.ght_cc_pcaddr_in.poke(0.U)
    dut.io.ght_cc_newcommit_out.expect(false.B)
    dut.clock.step(7) // clk+7

    //======== Commit 1 instruction before system initialisation
    dut.io.ght_cc_pcaddr_in.poke(0x10044.U)
    dut.io.ght_cc_newcommit_out.expect(false.B)
    dut.clock.step(1) // clk+1

    //======== inialised the system
    dut.io.ght_cc_pcaddr_in.poke(0x10040.U)
    dut.io.ght_cc_newcommit_out.expect(false.B)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.ght_cc_pcaddr_in.poke(0x10044.U)
    dut.io.ght_cc_newcommit_out.expect(true.B)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.ght_cc_pcaddr_in.poke(0x10048.U)
    dut.io.ght_cc_newcommit_out.expect(true.B)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.ght_cc_pcaddr_in.poke(0x1004C.U)
    dut.io.ght_cc_newcommit_out.expect(true.B)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.ght_cc_pcaddr_in.poke(0x10050.U)
    dut.io.ght_cc_newcommit_out.expect(true.B)
    dut.clock.step(1) // clk+1

    //======== no instruction is commit
    dut.io.ght_cc_newcommit_out.expect(false.B)
    dut.clock.step(1) // clk+1

    //======== commit an instruction
    dut.io.ght_cc_pcaddr_in.poke(0x10058.U)
    dut.io.ght_cc_newcommit_out.expect(true.B)
    dut.clock.step(1) // clk+1

    }
  }
  it should "All tested!"
}
