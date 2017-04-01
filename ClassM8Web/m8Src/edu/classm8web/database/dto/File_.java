package edu.classm8web.database.dto;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-30T22:03:30.861+0200")
@StaticMetamodel(File.class)
public class File_ {
	public static volatile SingularAttribute<File, Long> id;
	public static volatile SingularAttribute<File, String> fileName;
	public static volatile SingularAttribute<File, Date> uploadDate;
	public static volatile SingularAttribute<File, Long> contentSize;
	public static volatile SingularAttribute<File, String> contentType;
	public static volatile SingularAttribute<File, Schoolclass> referencedSchoolclass;
}
