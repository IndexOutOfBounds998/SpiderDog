import org.spiderdog.annotation.Field;
import org.spiderdog.annotation.Pager;
import org.spiderdog.annotation.Source;

import java.util.Arrays;

/**
 * Created by yang on 2019/8/13.
 */
@Source(url = "https://e-hentai.org/g/1463325/daee1c7e7f/")
@Pager(nextPageSeletor = "body > div:nth-child(10) > table > tbody > tr > td:nth-child(9)")
public class Model {
    @Field(selector = "#i1 > h1")
    private String title;

    @Field(selector = "#gdt > div > div > a", attr = "href", isNextUrl = true)
    private String[] imgs;

    @Field(selector = "#img", attr = "src")
    private String img;

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Model{" +
                "title='" + title + '\'' +
                ", imgs=" + Arrays.toString(imgs) +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
