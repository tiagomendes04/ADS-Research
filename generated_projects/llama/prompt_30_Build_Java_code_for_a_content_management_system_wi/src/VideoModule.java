public class VideoModule extends Module {
    private String video;

    public VideoModule(String id, String title, String content, String video) {
        super(id, title, content);
        this.video = video;
    }

    public String getVideo() {
        return video;
    }
}