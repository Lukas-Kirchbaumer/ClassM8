package com.example.backend.Mapper;

import android.widget.ArrayAdapter;

import com.example.backend.Dto.File;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.MappedObjects.MappedFile;
import com.example.backend.MappedObjects.MappedM8;
import com.example.backend.MappedObjects.MappedSchoolclass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;


public class ObjectMapper {

    public static MappedSchoolclass map(Schoolclass sc) {
        MappedSchoolclass msc = new MappedSchoolclass();
        msc.setName(sc.getName());
        msc.setId(sc.getId());
        if (sc.getPresident() != null)
            msc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
        if (sc.getPresidentDeputy() != null)
            msc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));
        msc.setRoom(sc.getRoom());
        msc.setSchool(sc.getSchool());
        ArrayList<MappedM8> mscClassMembers = new ArrayList<MappedM8>();
        Vector<MappedFile> mscFiles = new Vector<MappedFile>();

        for (M8 m8 : sc.getClassMembers()) {
            mscClassMembers.add(ObjectMapper.mapForSchoolClass(m8));
        }

        for (File f : sc.getFiles()) {
            mscFiles.add(ObjectMapper.mapForSchoolClasss(f));
        }

        msc.setFiles(mscFiles);
        msc.setClassMembers(mscClassMembers);
        return msc;
    }

    public static MappedM8 mapForSchoolClass(M8 m8) {
        MappedM8 mm8 = new MappedM8();
        mm8.setNewM8NoSchoolClass(m8);
        return mm8;
    }

    private static MappedFile mapForSchoolClasss(File f) {
        MappedFile mf = new MappedFile();
        mf.setNewFileNoSchoolClass(f);
        return mf;
    }

    public static MappedSchoolclass mapForM8(Schoolclass sc) {
        MappedSchoolclass mmc = new MappedSchoolclass();
        mmc.setId(sc.getId());
        mmc.setName(sc.getName());
        mmc.setRoom(sc.getRoom());
        mmc.setSchool(sc.getSchool());

        for (M8 member : sc.getClassMembers()) {
            mmc.getClassMembers().add(ObjectMapper.mapForSchoolClass(member));
        }

        mmc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
        mmc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));

        return mmc;

    }

    public static MappedM8 map(M8 m8) {
        MappedM8 mm8 = new MappedM8();
        mm8.setId(m8.getId());
        mm8.setEmail(m8.getEmail());
        mm8.setFirstname(m8.getFirstname());
        mm8.setLastname(m8.getLastname());
        mm8.setEmail(m8.getEmail());
        mm8.setHasVoted(m8.isHasVoted());
        mm8.setVotes(m8.getVotes());

        if (m8.getSchoolclass() != null) {
            mm8.setSchoolclass(ObjectMapper.mapForM8(m8.getSchoolclass()));
        }


        return mm8;
    }

    public static Vector<MappedM8> mapM8s(List<M8> m8s) {
        Vector<MappedM8> maped = new Vector<>();
        for (M8 m8 : m8s) {
            maped.add(ObjectMapper.mapForSchoolClass(m8));
        }
        return maped;

    }

    public static Vector<MappedSchoolclass> mapSchoolclasses(List<Schoolclass> schoolclasses) {
        Vector<MappedSchoolclass> maped = new Vector<>();
        for (Schoolclass s : schoolclasses) {
            maped.add(ObjectMapper.map(s));
        }
        return maped;

    }


}
