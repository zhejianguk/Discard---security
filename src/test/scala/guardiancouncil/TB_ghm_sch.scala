package guardiancouncil

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_ghm_sch extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "TB_ghm_sch"
  it should "Test guardiancouncil.TB_ghm_sch" in {
    val p = new GHM_SCH_Params (7)
    test(new GHM_SCH(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

    //==========================================================
    // initialisation
    //==========================================================
    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x00.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x1.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x00.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x01.U)
    dut.io.dest_little_core_id.expect(0x01.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x1.U)
    dut.io.big_core_opcode.poke(0x01.U)
    dut.io.dest_little_core_id.expect(0x02.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x2.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x03.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x04.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x2.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x04.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x7.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x05.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x06.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x7.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x06.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x03.U)
    dut.io.dest_little_core_id.expect(0x00.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x03.U)
    dut.io.dest_little_core_id.expect(0x01.U)
    dut.clock.step(1)

    dut.io.big_core_func.poke(0x0.U)
    dut.io.big_core_opcode.poke(0x00.U)
    dut.io.dest_little_core_id.expect(0x02.U)
    dut.clock.step(1)
    }
  }
  it should "All tested!"
}
