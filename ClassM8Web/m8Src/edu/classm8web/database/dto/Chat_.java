package edu.classm8web.database.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-30T22:03:30.858+0200")
@StaticMetamodel(Chat.class)
public class Chat_ {
	public static volatile SingularAttribute<Chat, Long> id;
	public static volatile ListAttribute<Chat, Message> messages;
}
