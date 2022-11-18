package com.sezo.protobuf.dao;

import java.util.Arrays;
import java.util.List;

import com.sezo.protobuf.AddressBookProtos;
import com.sezo.protobuf.AddressBookProtos.Person;

public class PersonDao {


	 public static List<Person> getPeople() {
		AddressBookProtos.Person sezayir=AddressBookProtos.Person.newBuilder()
				.setId(1).setName("sezayir")
				.setEmail("sezarx@gmail.com")
				.build();
		AddressBookProtos.Person cem=AddressBookProtos.Person.newBuilder()
				.setId(1).setName("Cem")
				.setEmail("cemx@gmail.com")
				.build();

		List<Person> people = Arrays.asList(sezayir,cem);
		return people;
	}


}