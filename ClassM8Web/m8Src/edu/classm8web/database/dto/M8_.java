package edu.classm8web.database.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-02T16:42:01.320+0200")
@StaticMetamodel(M8.class)
public class M8_ {
	public static volatile SingularAttribute<M8, Long> id;
	public static volatile SingularAttribute<M8, String> firstname;
	public static volatile SingularAttribute<M8, String> lastname;
	public static volatile SingularAttribute<M8, String> email;
	public static volatile SingularAttribute<M8, String> password;
	public static volatile SingularAttribute<M8, Boolean> hasVoted;
	public static volatile SingularAttribute<M8, Integer> votes;
	public static volatile SingularAttribute<M8, Schoolclass> schoolclass;
}
