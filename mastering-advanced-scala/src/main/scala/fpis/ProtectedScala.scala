package fpis {
  class ProtectedScala {
    protected def f() = { println("f") }
  }
  class Sub extends ProtectedScala {
    f()
  }
  class Other {
//    (new ProtectedScala).f()
  }
}

