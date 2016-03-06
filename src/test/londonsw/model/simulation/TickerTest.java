package londonsw.model.simulation;

import javafx.application.Application;
import javafx.stage.Stage;
import rx.Subscriber;

public class TickerTest extends Application {

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Ticker ticker = new Ticker();
        TestListener tl1 = new TestListener('A');
    }
}

class TestListener extends Subscriber<Long> {
    private char id;

    public TestListener(char id) {
        Ticker.subscribe(this);
        this.id = id;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onNext(Long aLong) {
        System.out.println("Id " + id + ": " + aLong);
    }
}