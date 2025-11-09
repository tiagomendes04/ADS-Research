public class Main {
    public static void main(String[] args) {
        CMS cms = new CMS();

        Page page1 = new Page("page1", "Page 1", "This is page 1");
        Page page2 = new Page("page2", "Page 2", "This is page 2");

        TextModule textModule1 = new TextModule("module1", "Text Module 1", "This is text module 1");
        TextModule textModule2 = new TextModule("module2", "Text Module 2", "This is text module 2");

        ImageModule imageModule = new ImageModule("module3", "Image Module", "This is image module", "image.jpg");

        VideoModule videoModule = new VideoModule("module4", "Video Module", "This is video module", "video.mp4");

        page1.addModule(textModule1);
        page1.addModule(imageModule);

        page2.addModule(textModule2);
        page2.addModule(videoModule);

        cms.addPage(page1);
        cms.addPage(page2);

        for (Page page : cms.getPages()) {
            System.out.println("Page ID: " + page.getId());
            System.out.println("Page Title: " + page.getTitle());
            System.out.println("Page Content: " + page.getContent());

            for (Module module : page.getModules()) {
                System.out.println("Module ID: " + module.getId());
                System.out.println("Module Title: " + module.getTitle());
                System.out.println("Module Content: " + module.getContent());

                if (module instanceof ImageModule) {
                    ImageModule imageModule1 = (ImageModule) module;
                    System.out.println("Image: " + imageModule1.getImage());
                } else if (module instanceof VideoModule) {
                    VideoModule videoModule1 = (VideoModule) module;
                    System.out.println("Video: " + videoModule1.getVideo());
                }
            }
        }
    }
}