package fpis;

public class PrivateTest {
    class Inner{
        private void test() {
            System.out.println("test");
        }
        class InnerMost {
            public void f(){
                test();
            }
        }
    }

    public void test(){
//        Inner in = new Inner();
//        in.test();
    }
}
