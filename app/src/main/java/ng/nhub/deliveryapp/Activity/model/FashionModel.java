package ng.nhub.deliveryapp.Activity.model;


public class FashionModel {
    private String name;
    private String price;
    private String qty;
    private int thumbnail;

    public FashionModel(){

    }

    public FashionModel(String name, String price, int thumbnail) {

        this.name = name;
        this.price = price;
        this.qty = qty;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
