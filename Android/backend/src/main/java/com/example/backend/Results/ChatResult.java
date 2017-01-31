package com.example.backend.Results;

import com.example.backend.Dto.SchoolclassChat;

public final class ChatResult {
    public final String type;
    public final boolean success;
    public final SchoolclassChat schoolclassChat;

    public ChatResult(String type, boolean success, SchoolclassChat schoolclassChat){
        this.type = type;
        this.success = success;
        this.schoolclassChat = schoolclassChat;
    }
}