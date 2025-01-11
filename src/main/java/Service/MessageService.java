package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if (message.getMessage_text().length() > 0)
            return messageDAO.insertMessage(message);
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(String id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(String id) {
        Message message = getMessageById(id);
        if (message != null)
            return messageDAO.deleteMessageById(message);
        return null;
    }

    public Message patchMessageById(String id, String message_text) {
        if (message_text.length() > 0 &&
            messageDAO.patchMessageById(id, message_text) != null
        )
            return getMessageById(id);
        return null;
    }

    public List<Message> getAllMessagesByUserId(String id) {
        return messageDAO.getAllMessagesByUserId(id);
    }
}
