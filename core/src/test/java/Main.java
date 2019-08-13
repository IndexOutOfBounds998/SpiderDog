import org.spiderdog.Spider.DefaultSpider;
import org.spiderdog.api.SpiderCall;

/**
 * Main
 * Created by yang on 2019/8/13.
 */
public class Main {


    public static void main(String[] args) {

        DefaultSpider.createSpiderDog(null, new SpiderCall<Model>() {
            @Override
            public void onSuccess(Model model) {
                System.out.println(model.getTitle());
            }
        }, new Model())
                .run();
    }
}
