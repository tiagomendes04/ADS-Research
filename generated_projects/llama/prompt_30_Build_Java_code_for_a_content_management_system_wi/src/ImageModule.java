public class ImageModule extends Module {
    private String image;

    public ImageModule(String id, String title, String content, String image) {
        super(id, title, content);
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}