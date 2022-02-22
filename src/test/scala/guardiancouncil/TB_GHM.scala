package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_GHM extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TB_GHM"
  it should "Test guardiancouncil.TB_ghm_sch" in {
    val p = new GHMParams (4, 202)
    test(new GHM(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

    //==========================================================
    // initialisation
    //==========================================================
    // 1st packet
    dut.io.ghm_packet_in.poke(0x03FFFFFF.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    dut.io.ghm_packet_in.poke(0x0.U)
    dut.io.ghm_packet_outs(0).expect(0x03FFFFFF.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    // 2nd packet
    dut.io.ghm_packet_in.poke(0x03FFFFF2.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    dut.io.ghm_packet_in.poke(0x0.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x03FFFFF2.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    // 3rd packet
    dut.io.ghm_packet_in.poke(0x03FFFFF4.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    dut.io.ghm_packet_in.poke(0x0.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x03FFFFF4.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    // 4th packet
    dut.io.ghm_packet_in.poke(0x03FFFFF9.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    dut.io.ghm_packet_in.poke(0x0.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x03FFFFF9.U)
    dut.clock.step(1)

    // 5th packet
    dut.io.ghm_packet_in.poke(0x03FFFF00.U)
    dut.io.ghm_packet_outs(0).expect(0x0.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    dut.io.ghm_packet_in.poke(0x0.U)
    dut.io.ghm_packet_outs(0).expect(0x03FFFF00.U)
    dut.io.ghm_packet_outs(1).expect(0x0.U)
    dut.io.ghm_packet_outs(2).expect(0x0.U)
    dut.io.ghm_packet_outs(3).expect(0x0.U)
    dut.clock.step(1)

    }
  }
  it should "All tested!"
}
