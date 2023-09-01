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
        return new ItemEntity(it.getTitle(), it.getDescription(), it.isCompleted(), it.getId());
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

        return new UserEntity(0,jsonObject.get("_id").getAsInt(),jsonObject.get("userId").getAsString(),
                jsonObject.get("payerBalance").getAsInt(),jsonObject.get("balance").getAsInt(),jsonObject.get("available").getAsInt(),
                jsonObject.get("contacts").getAsString(),jsonObject.get("favorites").getAsString(),jsonObject.get("history").getAsString(),
                jsonObject.get("contributors").getAsString(),jsonObject.get("sponsors").getAsString(),
                jsonObject.get("receiverBalance").getAsInt(),jsonObject.get("email").getAsString(),jsonObject.get("name").getAsString(),
                jsonObject.get("paypal").getAsString(),jsonObject.get("socketId").getAsString(),jsonObject.get("firebaseDeviceToken").getAsString(),
                jsonObject.get("payerEmail").getAsString(),jsonObject.get("images").getAsString(),jsonObject.get("coverImage").getAsString(),
                jsonObject.get("profileImage").getAsString());

        /*return new User(it.getPk(), it.getId(), it.getUserId(), it.getPayerBalance(),
                it.getBalance(), it.getAvailable(), it.getContacts(), it.getFavorites(),
                it.getHistory(), it.getContributors(), it.getSponsors(), it.getReceiverBalance(),
                it.getEmail(), it.getName(), it.getPaypal(), it.getSocketId(),
                it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
                it.getProfileImage());*/


    }

}