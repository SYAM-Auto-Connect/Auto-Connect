package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Conversation;
import com.codeup.autoconnect.models.Message;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.ConversationRepository;
import com.codeup.autoconnect.repositories.MessageRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import com.codeup.autoconnect.services.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserRepository usersDao;
    private final MessageRepository messagesDao;
    private final ConversationRepository conversationsDao;

    @Autowired
    public MessageController(MessageService messageService, UserRepository usersDao, MessageRepository messagesDao, ConversationRepository conversationsDao) {
        this.messageService = messageService;
        this.usersDao = usersDao;
        this.messagesDao = messagesDao;
        this.conversationsDao = conversationsDao;
    }

    @GetMapping
    public String index(Model model) {
        List<Message> messages = messageService.getMessages();
        List<Conversation> conversations = getExistingConversations(); // Fetch existing conversations
        model.addAttribute("messages", messages);
        model.addAttribute("conversations", conversations); // Add existing conversations to the model
        return "messages/message";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        // Retrieve the currently logged-in user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Find the user with the provided keyword
        User searchedUser = usersDao.findByUsername(keyword);

        if (searchedUser == null || searchedUser.equals(currentUser)) {
            // If the searched user does not exist or is the same as the current user, display an empty list
            model.addAttribute("searchResults", Collections.emptyList());
        } else {
            // Check if a conversation already exists between the current user and the searched user
            Conversation conversation = conversationsDao.findByUsers(currentUser.getId(), searchedUser.getId());

            if (conversation != null) {
                // If a conversation exists, display only that conversation
                model.addAttribute("searchResults", Collections.singletonList(conversation));
            } else {
                // If no conversation exists, display the searched user
                model.addAttribute("searchResults", Collections.singletonList(searchedUser));
            }
        }

        model.addAttribute("searchAttempted", true); // Set searchAttempted to true
        model.addAttribute("conversations", getExistingConversations()); // Add existing conversations to the model
        return "messages/message";
    }


    @PostMapping("/new-conversation")
    public String saveNewConversation(@RequestParam("recipientId") Long recipientId,
                                      @RequestParam("newMessage") String message,
                                      Model model) {
        User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User recipient = usersDao.findById(recipientId).orElse(null);

        if (recipient != null) {
            // Check if a conversation already exists between the sender and recipient
            Conversation existingConversation = conversationsDao.findByUsers(sender.getId(), recipientId);

            if (existingConversation != null) {
                // Conversation already exists, add the new message to the existing conversation
                messageService.sendNewMessage(message, existingConversation.getId(), recipient, sender);
            } else {
                // Conversation does not exist, create a new conversation
                Conversation conversation = new Conversation();
                conversation.setUser1(sender);
                conversation.setUser2(recipient);
                conversation = conversationsDao.save(conversation);

                // Save the message in the conversation
                messageService.sendNewMessage(message, conversation.getId(), recipient, sender);
            }
        }

        // Update the model to include the existing conversations
        List<Conversation> conversations = getExistingConversations();
        model.addAttribute("conversations", conversations);

        // Redirect to the messages page
        return "redirect:/messages";
    }



    @PostMapping("/send")
    public String sendMessage(@RequestParam("conversationId") Long conversationId,
                              @RequestParam("message") String message,
                              @RequestParam("recipientId") String recipientId,
                              Model model) {
        User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User recipient = usersDao.findById(Long.valueOf(recipientId)).orElse(null);
        messageService.sendMessage(conversationId, sender, message, recipient);

        // Update the model to include the existing conversations
        List<Conversation> conversations = getExistingConversations();
        model.addAttribute("conversations", conversations);

        return "redirect:/messages";
    }

    @GetMapping("/load")
    public String loadConversations(Model model) {
        List<Conversation> conversations = getExistingConversations();
        model.addAttribute("conversations", conversations);
        // Pass an empty list as searchResults to indicate no search results
        model.addAttribute("searchResults", Collections.emptyList());
        return "messages/message";
    }

    @GetMapping("/conversation/{recipientId}")
    @ResponseBody
    public Map<String, Object> getConversationId(@PathVariable Long recipientId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conversation conversation = conversationsDao.findByUsers(currentUser.getId(), recipientId);
        Map<String, Object> response = new HashMap<>();
        if (conversation != null) {
            response.put("conversationId", conversation.getId());
        }
        return response;
    }

    @GetMapping("/load-conversation/{conversationId}")
    public String loadConversation(@PathVariable Long conversationId, Model model) {
        Conversation conversation = conversationsDao.findById(conversationId).orElse(null);
        model.addAttribute("conversation", conversation);
        return "messages/conversation :: conversation";
    }

    private List<Conversation> getExistingConversations() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Conversation> conversations = conversationsDao.findByUser1(currentUser);

        List<Conversation> conversationsWithCurrentUserAsUser2 = conversationsDao.findByUser2(currentUser);
        conversations.addAll(conversationsWithCurrentUserAsUser2);

        // Remove duplicate conversations
        Set<Conversation> conversationSet = new HashSet<>(conversations);
        conversations = new ArrayList<>(conversationSet);

        return conversations;
    }
}
