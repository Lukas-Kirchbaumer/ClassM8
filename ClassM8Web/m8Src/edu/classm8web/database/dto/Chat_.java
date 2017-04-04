package edu.classm8web.database.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-02T15:56:10.759+0200")
@StaticMetamodel(Chat.class)
public class Chat_ {
	public static volatile SingularAttribute<Chat, Long> id;
	public static volatile ListAttribute<Chat, Message> messages;
}
