package bean;

/**
 * Created by John on 2017/1/1.
 */

public class FruitBean {
    private  String name;
    private  int  drawableId;

    public FruitBean(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
