package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_ght_dc extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TB_ght_dc"
  it should "Test guardiancouncil.TB_ght_dc" in {
    test(new GHT_DC()).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

    dut.io.ght_dc_newcommit_in.poke(true.B)
    dut.io.ght_dc_inst_in.poke(0x00001003.U)
    dut.io.ght_dc_inst_type.expect(0x01.B)
    dut.clock.step(1) // clk+1

    dut.io.ght_dc_newcommit_in.poke(true.B)
    dut.io.ght_dc_inst_in.poke(0x00001003.U)
    dut.io.ght_dc_inst_type.expect(0x1.U)
    dut.clock.step(1) // clk+1

    dut.io.ght_dc_newcommit_in.poke(false.B)
    dut.io.ght_dc_inst_in.poke(0x00001003.U)
    dut.io.ght_dc_inst_type.expect(0x0.U)
    dut.clock.step(1) // clk+1

    dut.io.ght_dc_newcommit_in.poke(true.B)
    dut.io.ght_dc_inst_in.poke(0x00000003.U)
    dut.io.ght_dc_inst_type.expect(0x1.U)
    dut.clock.step(1) // clk+1


    }
  }
  it should "All tested!"
}
