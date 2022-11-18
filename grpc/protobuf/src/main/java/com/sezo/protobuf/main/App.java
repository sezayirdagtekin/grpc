package com.sezo.protobuf.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.sezo.protobuf.AddressBookProtos;
import com.sezo.protobuf.AddressBookProtos.Person;
import com.sezo.protobuf.dao.PersonDao;

public class App {

	private static final String PATH = "c:\\test\\people.txt";

	public static void main(String[] args) throws IOException {

		List<Person> people = PersonDao.getPeople();

		write(people);

		read();

	}

	public static void read() throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(new File(PATH));

		AddressBookProtos.Addressbook deserialized = AddressBookProtos.Addressbook.newBuilder().mergeFrom(fis).build();

		List<Person> people = deserialized.getPeopleList();

		people.forEach(System.out::println);
	}

	public static void write(List<Person> people) throws FileNotFoundException, IOException {

		AddressBookProtos.Addressbook addressbook = AddressBookProtos.Addressbook.newBuilder().addAllPeople(people).build();

		FileOutputStream fos = new FileOutputStream(new File(PATH));

		addressbook.writeTo(fos);
	}

}
