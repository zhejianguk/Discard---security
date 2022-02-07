package fifo

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class TB_FIFO extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "FIFO"
  // test class body here
  it should "Test fifo.RegFifo" in {
  // test case body here
    val p = new FIFOParams (32, 4)
    test(new RegFifo(p)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
    // Inialisation
    dut.io.enq_ready.expect(true.B)
    dut.io.deq_valid.expect(false.B)
    dut.clock.step(1) // clk+1

    //======== Push 1st data ========//
    dut.io.enq_bits.poke(0x55.U)
    dut.io.enq_valid.poke(true.B)
    dut.clock.step(1)
    dut.io.enq_bits.poke(0x0FFFFFFF.U) // Invalid push
    dut.io.enq_valid.poke(false.B)
    // Check point
    dut.io.enq_ready.expect(true.B)
    dut.io.deq_valid.expect(true.B)
    dut.clock.step(1)

    //======== Push 2nd data ========//
    dut.io.enq_bits.poke(0x56.U)
    dut.io.enq_valid.poke(true.B)
    dut.clock.step(1)
    dut.io.enq_bits.poke(0x0FFFFFFF.U) // Invalid push
    dut.io.enq_valid.poke(false.B)
    // Check point
    dut.io.enq_ready.expect(true.B)
    dut.io.deq_valid.expect(true.B)
    dut.clock.step(1)

    //======== Push 3rd data ========//
    dut.io.enq_bits.poke(0x57.U)
    dut.io.enq_valid.poke(true.B)
    dut.clock.step(1)
    dut.io.enq_bits.poke(0x0FFFFFFF.U) // Invalid push
    dut.io.enq_valid.poke(false.B)
    // Check point
    dut.io.enq_ready.expect(true.B)
    dut.io.deq_valid.expect(true.B)
    dut.clock.step(1)


    //======== Push 4th data ========//
    dut.io.enq_bits.poke(0x58.U)
    dut.io.enq_valid.poke(true.B)
    dut.clock.step(1)
    dut.io.enq_bits.poke(0x0FFFFFFF.U) // Invalid push
    dut.io.enq_valid.poke(false.B)
    // Check point
    dut.io.enq_ready.expect(false.B)
    dut.io.deq_valid.expect(true.B)
    dut.clock.step(7)


    //======== Pull all data ========//
    dut.io.deq_ready.poke(true.B)
    // Check point
    dut.io.deq_bits.expect(0x55.U)
    dut.clock.step(1)
    dut.io.deq_ready.poke(false.B)
    dut.clock.step(1)

    dut.io.deq_ready.poke(true.B)
    // Check point
    dut.io.deq_bits.expect(0x56.U)
    dut.clock.step(1)
    dut.io.deq_ready.poke(false.B)
    dut.clock.step(1)

    dut.io.deq_ready.poke(true.B)
    // Check point
    dut.io.deq_bits.expect(0x57.U)
    dut.clock.step(1)
    dut.io.deq_ready.poke(false.B)
    dut.clock.step(1)

    dut.io.deq_ready.poke(true.B)
    // Check point
    dut.io.deq_bits.expect(0x58.U)
    dut.clock.step(1)
    dut.io.enq_ready.expect(true.B)
    dut.io.deq_valid.expect(false.B)
    dut.io.deq_ready.poke(false.B)
    dut.clock.step(1)

    }
  }
  it should "All tested!"
}
