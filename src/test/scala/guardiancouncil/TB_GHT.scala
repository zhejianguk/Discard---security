package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_GHT extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "GHT"
  // test class body here
  it should "Test guardiancouncil.GHT" in {
  // test case body here
    val p = new GHTParams (40, 64)
    test(new GHT(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
    // test body here
    dut.io.core_reset_vector_in.poke(0x10040.U)
    dut.io.ght_pc_count_out.expect(0.U)

    //============ Commit 1 instruction ============
    dut.clock.step(10) // clk+10
    dut.io.core_pc_in.poke(0x10044.U) // PC+1

    dut.clock.step(1) // clk+1
    dut.io.ght_pc_count_out.expect(1.U)

    //============ Commit 2 instruction ============
    dut.clock.step(2) // clk+10
    dut.io.core_pc_in.poke(0x10048.U) // PC+1

    dut.clock.step(1) // clk+10
    dut.io.core_pc_in.poke(0x10800.U) // PC JUmp
    dut.clock.step(1) // clk+1
    dut.io.ght_pc_count_out.expect(3.U)
    }
  }
  it should "All tested!"
}
