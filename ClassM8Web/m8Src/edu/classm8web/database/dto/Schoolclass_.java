package edu.classm8web.database.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-02T15:56:10.886+0200")
@StaticMetamodel(Schoolclass.class)
public class Schoolclass_ {
	public static volatile SingularAttribute<Schoolclass, Long> id;
	public static volatile ListAttribute<Schoolclass, M8> classMembers;
	public static volatile ListAttribute<Schoolclass, File> files;
	public static volatile ListAttribute<Schoolclass, Emote> emotes;
	public static volatile SingularAttribute<Schoolclass, String> name;
	public static volatile SingularAttribute<Schoolclass, String> room;
	public static volatile SingularAttribute<Schoolclass, M8> president;
	public static volatile SingularAttribute<Schoolclass, M8> presidentDeputy;
	public static volatile SingularAttribute<Schoolclass, String> school;
	public static volatile SingularAttribute<Schoolclass, Chat> schoolclassChat;
}
