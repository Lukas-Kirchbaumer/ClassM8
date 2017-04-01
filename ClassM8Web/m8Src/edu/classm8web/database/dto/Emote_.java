package edu.classm8web.database.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-30T22:03:30.860+0200")
@StaticMetamodel(Emote.class)
public class Emote_ {
	public static volatile SingularAttribute<Emote, Long> id;
	public static volatile SingularAttribute<Emote, String> fileName;
	public static volatile SingularAttribute<Emote, String> shortString;
	public static volatile SingularAttribute<Emote, Long> contentSize;
	public static volatile SingularAttribute<Emote, Schoolclass> referencedSchoolclass;
}
