import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class SMSNotification implements Notification {
    private String to;
    private String message;

    public SMSNotification(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public void send() {
        Account account = new Account("your-account_sid", "your-auth_token");
        MessageCreator creator = new MessageCreator(
                new PhoneNumber(to),
                new PhoneNumber("your-twilio-phone-number"),
                message
        );
        Message message = Message.creator(creator).create(account);
        System.out.println("SMS sent to " + to);
    }
}