package londonsw.model.simulation;

public class TickerTest {

    public static void main(String[] args) throws InterruptedException {
        runTickerTest();
    }

    public static void runTickerTest() throws InterruptedException {
        Ticker ticker = new Ticker();
        TestListener tl1 = new TestListener(1);
        ticker.start();
        Thread.sleep(3000);
        TestListener tl2 = new TestListener(2);
    }

}

class TestListener implements TickerListener {
    private int id;

    public TestListener(int id) {
        Ticker.subscribe(this);
        this.id = id;
    }

    @Override
    public void onTick(long time) {
        System.out.println("ID " + id + " heard the tick, time is " + time);
    }
}