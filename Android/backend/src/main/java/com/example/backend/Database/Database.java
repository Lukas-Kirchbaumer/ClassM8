package com.example.backend.Database;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.example.backend.Dto.Emote;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Message;
import com.example.backend.Dto.Position;
import com.example.backend.Dto.Schoolclass;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by laubi on 12/21/2016.
 */
public class Database {
    private static Database ourInstance = new Database();
    private ArrayList<Message> messages = new ArrayList<>();

    private ArrayList<Position> positions = new ArrayList<>();
    private ArrayList<Emote> emojis = new ArrayList<>();
    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public static Database getInstance() {
        return ourInstance;
    }

    public ArrayList<Emote> getEmojis() {
        return emojis;
    }

    public void setEmojis(ArrayList<Emote> emojis) {
        this.emojis = emojis;
    }

    private M8 currentMate;
    private Schoolclass currentSchoolclass;

    private Database() {
    }

    public M8 getCurrentMate() {
        return currentMate;
    }

    public void setCurrentMate(M8 currentMate) {
        this.currentMate = currentMate;
    }

    public Schoolclass getCurrentSchoolclass() {
        return currentSchoolclass;
    }

    public void setCurrentSchoolclass(Schoolclass currentSchoolclass) {
        this.currentSchoolclass = currentSchoolclass;
    }

   /* public int addMultipleMessages(Collection<Message> messages) {
        int counter = 0;
        for (Message m : messages) {
            if (!this.messages.contains(m)) {
                this.messages.add(this.messages.size(), m);
                counter++;
            }
        }
        return counter;
    }*/

    public boolean addSingleMessage(Message message){
        boolean containing = this.messages.contains(message);
        if (!containing) {
            this.messages.add(this.messages.size(), message);
        }
        return !containing;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ObservableArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addLocalMessage(Message message, Context x) {
        MessageDbOperations operations = new MessageDbOperations(x);
        operations.open();
        operations.addMessage(message, (int) this.getCurrentMate().getId());
        operations.close();
        this.getSizeMessages(x);
    }

    public void getSizeMessages(Context x){
        MessageDbOperations operations = new MessageDbOperations(x);
        operations.open();
        //operations.delete();
        operations.size((int) this.getCurrentMate().getId());
        operations.close();
    }

    public ArrayList<Message> getLocalMessagesOfCurrentUser(Context x) {
        MessageDbOperations operations = new MessageDbOperations(x);
        operations.open();
        ArrayList<Message> messages = operations.getAllMessagesOfUser((int) this.getCurrentMate().getId());
        operations.close();
        this.messages.addAll(messages);
        return messages;
    }

    public String getTimeStampOfLastMessageSentToCurrentUser(Context x) {
        MessageDbOperations operations = new MessageDbOperations(x);
        operations.open();
        String timestamp = operations.getMaxDate((int) this.getCurrentMate().getId());
        operations.close();
        return timestamp;
    }
}
