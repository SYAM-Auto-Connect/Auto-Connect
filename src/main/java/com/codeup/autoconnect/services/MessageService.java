package com.codeup.autoconnect.services;

import com.codeup.autoconnect.models.Conversation;
import com.codeup.autoconnect.models.Message;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.ConversationRepository;
import com.codeup.autoconnect.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    public Message sendMessage(Long conversationId, User sender, String messageContent) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setMessage(messageContent);

        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message sendAMessage(Message message) {
        return message;
    }

    public void sendNewMessage(String message, Long conversationId, User recipient, User sender) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setSender(sender);
        newMessage.setRecipient(recipient);
        newMessage.setConversation(conversation); // Set the conversation

        // Add the message to the conversation
        conversation.getMessages().add(newMessage);

        conversationRepository.save(conversation); // Save the updated conversation
    }
}