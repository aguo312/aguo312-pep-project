package Controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginAccountHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount != null)
            context.json(mapper.writeValueAsString(addedAccount));
        else
            context.status(400);
    }

    private void postLoginAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account existingAccount = accountService.loginAccount(account);
        if (existingAccount != null)
            context.json(mapper.writeValueAsString(existingAccount));
        else
            context.status(401);
    }

    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null)
            context.json(mapper.writeValueAsString(addedMessage));
        else
            context.status(400);
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        String id = context.pathParam("message_id");
        Message message = messageService.getMessageById(id);
        if (message != null)
            context.json(message);
    }

    private void deleteMessageByIdHandler(Context context) {
        String id = context.pathParam("message_id");
        Message message = messageService.getMessageById(id);
        if (message != null)
            context.json(message);
    }

    private void patchMessageByIdHandler(Context context) throws JsonProcessingException {
        String id = context.pathParam("message_id");
        ObjectMapper mapper = new ObjectMapper();
        String message_text = mapper.readValue(context.body(), ObjectNode.class).get("message_text").asText();
        Message message = messageService.patchMessageById(id, message_text);
        if (message != null)
            context.json(message);
        else
            context.status(400);
    }

    private void getAllMessagesByUserHandler(Context context) {
        String id = context.pathParam("account_id");
        List<Message> messages = messageService.getAllMessagesByUserId(id);
        context.json(messages);
    }
}