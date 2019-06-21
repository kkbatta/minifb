package com.interview.pocketfb.utils;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.interview.pocketfb.UsersManager;
import com.interview.pocketfb.error.CustomException;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.sample.model.User;

public class GenericUtils {

	/**
	 * Add all kinds of user in put validations
	 */
	public static boolean validateUser(User user) throws Exception {
		if (user != null && StringUtils.isNotEmpty(user.getDob()) && StringUtils.isNotEmpty(user.getUserid())
				&& StringUtils.isNotEmpty(user.getPswd()) && StringUtils.isNotEmpty(user.getName())) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean validateCredentials(User user) throws Exception {
		if (user != null && StringUtils.isNotEmpty(user.getUserid() )) {
			User existingUser = UsersManager.INSTANCE.getUsers().get(user.getUserid());
			if (existingUser != null && existingUser.getPswd().equals(user.getPswd())) {
				return true;
			} else {
				throw new CustomException(
						MessageFormat.format("Requested User {0}  credentials dont match for deletion", user.getUserid()),
						ErrorCode.EC_2002);
			}
		}
		return false;
	}

	private static class JsonNodeAdapter extends TypeAdapter<JsonNode> {
		@Override
		public JsonNode read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.BEGIN_OBJECT) {
				JsonElement element = new JsonParser().parse(in);
				return new ObjectMapper().readTree(element.toString());
			} else {
				return null;
			}
		}

		@Override
		public void write(JsonWriter out, JsonNode value) throws IOException {
			if (value != null) {
				out.jsonValue(value.toString());
			} else {
				out.nullValue();
			}
		}
	}

	/**
	 * Could not get to finish this for bse respose selerialization
	 * 
	 * @param obj
	 * @return
	 */
	private JsonElement toJson(Object obj) {
		String str = new GsonBuilder().registerTypeAdapter(JsonNode.class, new JsonNodeAdapter()).create().toJson(obj);
		return (new JsonParser().parse(str));
	}
	
	public static User getPublicData(User user) {
		User pUser = new User();
		pUser.setName(user.getName());
		pUser.setUserid(user.getUserid());
		pUser.setEmail(user.getEmail());
		return pUser;
	}
}
