package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_GHT extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TB_GHT"
  it should "Test guardiancouncil.TB_GHT" in {
    val p = new GHTParams (40)
    test(new GHT(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      //==========================================================
      // initialisation
      //==========================================================
      dut.io.resetvector_in.poke(0x10040.U)
      dut.io.ght_pcaddr_in.poke(0.U)
      dut.io.ght_inst_in.poke(0.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+10

      //======== Commit 1 instruction before system initialisation
      dut.io.ght_pcaddr_in.poke(0x10044.U)
      dut.io.ght_inst_in.poke(0x00001003.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+10


      //======== inialised the system
      dut.io.ght_pcaddr_in.poke(0x10040.U)
      dut.io.ght_inst_in.poke(0x00001003.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x10044.U)
      dut.io.ght_inst_in.poke(0x00001003.U)
      dut.io.ght_inst_type.expect(1.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x10048.U)
      dut.io.ght_inst_in.poke(0x00000003.U)
      dut.io.ght_inst_type.expect(1.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x1004C.U)
      dut.io.ght_inst_in.poke(0x00000001.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x1004C.U)
      dut.io.ght_inst_in.poke(0x00000001.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x10050.U)
      dut.io.ght_inst_in.poke(0x00000003.U)
      dut.io.ght_inst_type.expect(1.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x10050.U)
      dut.io.ght_inst_in.poke(0x00000003.U)
      dut.io.ght_inst_type.expect(0.U)
      dut.clock.step(1) // clk+1

      dut.io.ght_pcaddr_in.poke(0x10054.U)
      dut.io.ght_inst_in.poke(0x00001003.U)
      dut.io.ght_inst_type.expect(1.U)
      dut.clock.step(1) // clk+1
    }
  }
  it should "All tested!"
}
