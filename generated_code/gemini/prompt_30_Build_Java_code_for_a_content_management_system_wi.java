```java
// Content Management System (CMS) - Simple Example

import java.util.ArrayList;
import java.util.List;

public class CMS {

    public static void main(String[] args) {
        // Create pages
        Page homePage = new Page("Home", "Welcome to our website!", "HomeModule");
        Page aboutPage = new Page("About Us", "Learn more about our company.", "AboutUsModule");
        Page contactPage = new Page("Contact", "Get in touch with us.", "ContactFormModule");

        // Create modules
        Module homeModule = new HomeModule("Welcome message", "Image.jpg");
        Module aboutUsModule = new AboutUsModule("Our history", "Our team");
        Module contactFormModule = new ContactFormModule("Name", "Email", "Message");


        // Add modules to pages (associate by module name)  This is very basic.
        homePage.addModuleData("Welcome message", "Image.jpg"); //Manually passing module data
        aboutPage.addModuleData("Our history", "Our team");
        contactPage.addModuleData("Contact details", "Form fields");


        // Display pages
        System.out.println(homePage.render());
        System.out.println(aboutPage.render());
        System.out.println(contactPage.render());
    }
}


// Page Class
class Page {
    private String title;
    private String content;
    private String moduleName;  // To associate with a module (very basic)
    private List<String> moduleData = new ArrayList<>();

    public Page(String title, String content, String moduleName) {
        this.title = title;
        this.content = content;
        this.moduleName = moduleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void addModuleData(String... data) {
        for(String d : data) {
            moduleData.add(d);
        }
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>").append(title).append("</h1>\n");
        sb.append("<p>").append(content).append("</p>\n");

        sb.append("<div>Module Data: ").append(String.join(", ", moduleData)).append("</div>"); // Simple module data display
        return sb.toString();
    }
}



// Module Interface
interface Module {
    String render();
}


// Home Module
class HomeModule implements Module {
    private String welcomeMessage;
    private String imagePath;

    public HomeModule(String welcomeMessage, String imagePath) {
        this.welcomeMessage = welcomeMessage;
        this.imagePath = imagePath;
    }

    @Override
    public String render() {
        return "<h2>Home Module</h2>\n" +
               "<p>" + welcomeMessage + "</p>\n" +
               "<img src='" + imagePath + "' alt='Home Image'>";
    }
}


// About Us Module
class AboutUsModule implements Module {
    private String history;
    private String teamInfo;

    public AboutUsModule(String history, String teamInfo) {
        this.history = history;
        this.teamInfo = teamInfo;
    }

    @Override
    public String render() {
        return "<h2>About Us Module</h2>\n" +
               "<p>History: " + history + "</p>\n" +
               "<p>Team: " + teamInfo + "</p>";
    }
}


// Contact Form Module
class ContactFormModule implements Module {
    private String nameField;
    private String emailField;
    private String messageField;

    public ContactFormModule(String nameField, String emailField, String messageField) {
        this.nameField = nameField;
        this.emailField = emailField;
        this.messageField = messageField;
    }

    @Override
    public String render() {
        return "<h2>Contact Form Module</h2>\n" +
               "<form>\n" +
               "  <label for='name'>Name:</label><br>\n" +
               "  <input type='text' id='name' name='name' value='" + nameField + "'><br><br>\n" +
               "  <label for='email'>Email:</label><br>\n" +
               "  <input type='email' id='email' name='email' value='" + emailField + "'><br><br>\n" +
               "  <label for='message'>Message:</label><br>\n" +
               "  <textarea id='message' name='message'>" + messageField + "</textarea><br><br>\n" +
               "  <input type='submit' value='Submit'>\n" +
               "</form>";
    }
}
```