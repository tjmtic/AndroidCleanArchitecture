package com.tiphubapps.ax.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tiphubapps.ax.data.entity.UserEntity;
import com.tiphubapps.ax.data.repository.dataSource.ItemEntity;
import com.tiphubapps.ax.data.repository.dataSource.Result;
import com.tiphubapps.ax.domain.model.Item;
import com.tiphubapps.ax.domain.model.User;
import com.tiphubapps.ax.domain.repository.UseCaseResult;

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

    @TypeConverter
    public static Item itemFromItemEntity(ItemEntity it){
        return new Item(it.getTitle(), it.getDescription(), it.isCompleted(), it.getId());
    }

    @TypeConverter
    public static ItemEntity itemEntityFromItem(Item it){
        return new ItemEntity(it.getTitle(), it.getDescription(), it.isCompleted(), false, it.getId());
    }

    /*@TypeConverter
    public static Result<Item> itemResultFromItemEntityResult(Result<ItemEntity> result){

    }*/

    /*public static UseCaseResult<Item> itemResultFromItemEntityResult(Result<ItemEntity> resp){
        UseCaseResult<Item> result;
        if (resp instanceof Result.Loading) {
            result = ((UseCaseResult)UseCaseResult.Loading);
        } else if (resp instanceof Result.Error) {
            result = new UseCaseResult.UseCaseError(((Result.Error)resp).getException());
        } else if (resp instanceof Result.Success) {
            UseCaseResult.UseCaseSuccess<Item> success = new UseCaseResult.UseCaseSuccess<>(Converters.itemFromItemEntity(resp.getData()));
            result = success;
        } else {
            // Handle unexpected case
            result = new UseCaseResult.Loading();
        }

        return result;
    }*/

    public static UserEntity userEntityFromJsonObject(JsonObject jsonObject){

        String id = !jsonObject.get("_id").isJsonNull() ? jsonObject.get("_id").getAsString() : "0";
        String userId = !jsonObject.get("_id").isJsonNull() ? jsonObject.get("_id").getAsString() : "0";
        int payerBalance = !jsonObject.get("payerBalance").isJsonNull() ? jsonObject.get("payerBalance").getAsInt() : -1;
        int balance = !jsonObject.get("balance").isJsonNull() ? jsonObject.get("balance").getAsInt() : -1;
        int  available = !jsonObject.get("available").isJsonNull() ? jsonObject.get("available").getAsInt() : -1;

        String contacts = !jsonObject.get("contacts").isJsonNull() ? jsonObject.get("contacts").getAsString() : "[]";
        String favorites = !jsonObject.get("favorites").isJsonNull() ? jsonObject.get("favorites").getAsString() : "[]";
        String history = !jsonObject.get("history").isJsonNull() ? jsonObject.get("history").getAsString() : "[]";
        String contributors = !jsonObject.get("contributors").isJsonNull() ? jsonObject.get("contributors").getAsString() : "[]";
        String sponsors = !jsonObject.get("sponsors").isJsonNull() ? jsonObject.get("sponsors").getAsString() : "[]";

        int receiverBalance = !jsonObject.get("receiverBalance").isJsonNull() ? jsonObject.get("receiverBalance").getAsInt() : 0;
        String email = !jsonObject.get("email").isJsonNull() ? jsonObject.get("email").getAsString() : "0";
        String name = !jsonObject.get("name").isJsonNull() ? jsonObject.get("name").getAsString() : "0";
        String paypal = !jsonObject.get("paypal").isJsonNull() ? jsonObject.get("paypal").getAsString() : "0";
        String socketId = !jsonObject.get("socketId").isJsonNull() ? jsonObject.get("socketId").getAsString() : "0";
        String firebaseDeviceToken = !jsonObject.get("firebaseDeviceToken").isJsonNull() ? jsonObject.get("firebaseDeviceToken").getAsString() : "0";
        String payerEmail = !jsonObject.get("payerEmail").isJsonNull() ? jsonObject.get("payerEmail").getAsString() : "0";

        String images = !jsonObject.get("images").isJsonNull() ? jsonObject.get("images").getAsString() : "[]";
        String coverImage = !jsonObject.get("coverImage").isJsonNull() ? jsonObject.get("coverImage").getAsString() : "";
        String profileImage = !jsonObject.get("profileImage").isJsonNull() ? jsonObject.get("profileImage").getAsString() : "";

        return new UserEntity(0,id,userId,
                payerBalance,balance,available,
                contacts,favorites,history,
                contributors,sponsors,
                receiverBalance,email,name,
                paypal,socketId,firebaseDeviceToken,
                payerEmail,images,coverImage,
                profileImage);

        /*return new User(it.getPk(), it.getId(), it.getUserId(), it.getPayerBalance(),
                it.getBalance(), it.getAvailable(), it.getContacts(), it.getFavorites(),
                it.getHistory(), it.getContributors(), it.getSponsors(), it.getReceiverBalance(),
                it.getEmail(), it.getName(), it.getPaypal(), it.getSocketId(),
                it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
                it.getProfileImage());*/


    }

    public static UserEntity userEntityFromUser(User it){

        /*return new UserEntity(0,jsonObject.get("_id").getAsInt(),jsonObject.get("userId").getAsString(),
                jsonObject.get("payerBalance").getAsInt(),jsonObject.get("balance").getAsInt(),jsonObject.get("available").getAsInt(),
                jsonObject.get("contacts").getAsString(),jsonObject.get("favorites").getAsString(),jsonObject.get("history").getAsString(),
                jsonObject.get("contributors").getAsString(),jsonObject.get("sponsors").getAsString(),
                jsonObject.get("receiverBalance").getAsInt(),jsonObject.get("email").getAsString(),jsonObject.get("name").getAsString(),
                jsonObject.get("paypal").getAsString(),jsonObject.get("socketId").getAsString(),jsonObject.get("firebaseDeviceToken").getAsString(),
                jsonObject.get("payerEmail").getAsString(),jsonObject.get("images").getAsString(),jsonObject.get("coverImage").getAsString(),
                jsonObject.get("profileImage").getAsString());*/

        return new UserEntity(it.getPk(), it.getId(), it.getUserId(), it.getPayerBalance(),
                it.getBalance(), it.getAvailable(), it.getContacts(), it.getFavorites(),
                it.getHistory(), it.getContributors(), it.getSponsors(), it.getReceiverBalance(),
                it.getEmail(), it.getName(), it.getPaypal(), it.getSocketId(),
                it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
                it.getProfileImage());


    }

}