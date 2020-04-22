
import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        Observable<String> ob = Observable.just("Hello");
        ob.subscribe(System.out::print);
    }
}
