package com.tiphubapps.ax.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiphubapps.ax.data.entity.UserEntity;
import com.tiphubapps.ax.domain.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static User userFromUserEntity(UserEntity it){
       return new User(it.getPk(), it.getId(), it.getUserId(), it.getPayerBalance(), it.getBalance(), it.getAvailable(), it.getContacts(), it.getFavorites(), it.getHistory(), it.getContributors(), it.getSponsors(), it.getReceiverBalance(), it.getEmail(), it.getName(), it.getPaypal(), it.getSocketId(), it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(), it.getProfileImage());
    }

}