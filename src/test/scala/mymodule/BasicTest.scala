package mymodule

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class BasicTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "MyModule"
  // test class body here
  it should "do manythings" in {
  // test case body here
    test(new MyModule) { c =>
    // test body here
    c.io.in.poke(0.U)
    c.clock.step()
    c.io.out.expect(0.U)
    c.io.in.poke(42.U)
    c.clock.step()
    c.io.out.expect(42.U)
    println("Last output value :" + c.io.out.peek().litValue)
    }
  }
}
