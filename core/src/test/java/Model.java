import org.spiderdog.annotation.Field;
import org.spiderdog.annotation.PageScan;
import org.spiderdog.annotation.Pager;
import org.spiderdog.annotation.Source;

/**
 * Model
 * Created by yang on 2019/8/13.
 */
@Source(url = "https://e-hentai.org/g/1463325/daee1c7e7f/")
@Pager(nextUrlSeletor = "body > div:nth-child(9) > table > tbody > tr > td:nth-child(11) > a", attr = "href")
@PageScan(seletor = "#gdt > div > div > a", attr = "href")
public class Model {
    @Field(selector = "#i1 > h1")
    private String title;

    @Field(selector = "#img", attr = "src")
    private String img;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
