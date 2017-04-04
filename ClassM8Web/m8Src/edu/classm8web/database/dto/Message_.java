package edu.classm8web.database.dto;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-02T15:56:10.876+0200")
@StaticMetamodel(Message.class)
public class Message_ {
	public static volatile SingularAttribute<Message, String> content;
	public static volatile SingularAttribute<Message, String> sender;
	public static volatile SingularAttribute<Message, Timestamp> dateTime;
}
