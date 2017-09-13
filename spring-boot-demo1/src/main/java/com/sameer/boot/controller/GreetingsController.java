package com.sameer.boot.controller;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sameer.boot.entity.Greeting;

@RestController
public class GreetingsController {

	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingsMap;

	public static Greeting save(Greeting greeting) {
		if (greetingsMap == null) {
			greetingsMap = new HashMap();
			nextId = BigInteger.ONE;
		}
		nextId=nextId.add(BigInteger.ONE);
		greeting.setId(nextId);
		greetingsMap.put(nextId, greeting);
		return greeting;
	}

	static {
		Greeting greeting1 = new Greeting();
		greeting1.setText("My Text1");
		save(greeting1);

		Greeting greeting2 = new Greeting();
		greeting2.setText("My Text2");
		save(greeting2);
	}

	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		Collection<Greeting> greetings = greetingsMap.values();
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreetingsById(@PathVariable ("id") BigInteger id) {
		Greeting greeting = greetingsMap.get(id);
		if(greeting==null)
		{
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}

}
