package programcounter

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec




class BasicTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "Counter"
  // test class body here
  it should "Test programcounter.Counter" in {
  // test case body here
    test(new Counter(32)) { dut =>
    // test body here
    dut.io.trigger.poke(true.B)

    dut.clock.step(1) // clk: 2
    dut.io.trigger.poke(false.B)
    dut.io.counter_out.expect(1.U)

    dut.clock.step(1) // clk: 3
    dut.io.trigger.poke(true.B)
    dut.io.counter_out.expect(1.U)

    dut.clock.step(2) // clk: 4
    dut.io.counter_out.expect(3.U)
    }
  }
  it should "All tested!"
}
