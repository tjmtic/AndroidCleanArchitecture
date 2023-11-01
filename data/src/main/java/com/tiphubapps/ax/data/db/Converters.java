package com.tiphubapps.ax.data.db;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
/*
    @TypeConverter
    public static String fromJsonArray(JsonArray list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
*/

    @TypeConverter
    public static User userFromUserEntity(UserEntity it){
       return new User(it.getPk(), it.getId(), it.getUserId(), it.getFrom(), it.getPayerBalance(), it.getReceiverBalance(),
               it.getAvailable(), it.getContacts(), it.getFavorites(), it.getHistory(), it.getContributors(), it.getSponsors(),
               it.getName(), it.getSocketId(), it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
               it.getProfileImage(), it.getWsSocketId(), it.getOnboardingLink(), it.getUsername(), it.getVerificationCode(),
               it.getVerified(), it.getStripeId(), it.getAppVersion(), it.getTransferBalance(), it.getPaymentFlagged(),
               it.getOnboarded(), it.getCreatedAt(), it.getUpdatedAt(), it.getSocialLinks());
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
        try {


            String id = !jsonObject.get("_id").isJsonNull() ? jsonObject.get("_id").getAsString() : "0";
            String userId = !jsonObject.get("_id").isJsonNull() ? jsonObject.get("_id").getAsString() : "0";
            int payerBalance = !jsonObject.get("payerBalance").isJsonNull() ? jsonObject.get("payerBalance").getAsInt() : -1;
//        int balance = !jsonObject.get("balance").isJsonNull() ? jsonObject.get("balance").getAsInt() : -1;
            int available = !jsonObject.get("available").isJsonNull() ? jsonObject.get("available").getAsInt() : -1;

            JsonArray contacts = !(jsonObject.get("contacts") == null || jsonObject.get("contacts").isJsonNull()) ? jsonObject.get("contacts").getAsJsonArray() : new JsonArray();
            JsonArray favorites = !jsonObject.get("favorites").isJsonNull() ? jsonObject.get("favorites").getAsJsonArray() : new JsonArray();
            JsonArray history = !(jsonObject.get("history") == null || jsonObject.get("history").isJsonNull()) ? jsonObject.get("history").getAsJsonArray() : new JsonArray();
            JsonArray contributors = !jsonObject.get("contributors").isJsonNull() ? jsonObject.get("contributors").getAsJsonArray() : new JsonArray();
            JsonArray sponsors = !jsonObject.get("sponsors").isJsonNull() ? jsonObject.get("sponsors").getAsJsonArray() : new JsonArray();

            int receiverBalance = !jsonObject.get("receiverBalance").isJsonNull() ? jsonObject.get("receiverBalance").getAsInt() : 0;
//        String email = !jsonObject.get("email").isJsonNull() ? jsonObject.get("email").getAsString() : "0";
            String name = !jsonObject.get("name").isJsonNull() ? jsonObject.get("name").getAsString() : "0";
//        String paypal = !jsonObject.get("paypal").isJsonNull() ? jsonObject.get("paypal").getAsString() : "0";
            String socketId = !jsonObject.get("socketId").isJsonNull() ? jsonObject.get("socketId").getAsString() : "0";
            String firebaseDeviceToken = !jsonObject.get("firebaseDeviceToken").isJsonNull() ? jsonObject.get("firebaseDeviceToken").getAsString() : "0";
            String payerEmail = !(jsonObject.get("payerEmail") == null || jsonObject.get("payerEmail").isJsonNull()) ? jsonObject.get("payerEmail").getAsString() : "0";

            JsonArray images = !jsonObject.get("images").isJsonNull() ? jsonObject.get("images").getAsJsonArray() : new JsonArray();

            String coverImage = "";//images.filter{} !jsonObject.get("coverImage").isJsonNull() ? jsonObject.get("coverImage").getAsString() : "";
            String profileImage = "";//!jsonObject.get("profileImage").isJsonNull() ? jsonObject.get("profileImage").getAsString() : "";

            for (JsonElement jObj : images
            ) {
                if (jObj.getAsJsonObject().get("type").getAsString().equals("cover")) {
                    coverImage = jObj.getAsJsonObject().get("url").getAsString();
                } else if (jObj.getAsJsonObject().get("type").getAsString().equals("profile")) {
                    profileImage = jObj.getAsJsonObject().get("url").getAsString();
                }

            }

            String wsSocketId = !jsonObject.get("wsSocketId").isJsonNull() ? jsonObject.get("wsSocketId").getAsString() : "0";
            String onboardingLink = !jsonObject.get("onboardingLink").isJsonNull() ? jsonObject.get("onboardingLink").getAsString() : "0";
            String username = !jsonObject.get("username").isJsonNull() ? jsonObject.get("username").getAsString() : "0";
            String verificationCode = !jsonObject.get("verificationCode").isJsonNull() ? jsonObject.get("verificationCode").getAsString() : "0";
            Boolean verified = !jsonObject.get("verified").isJsonNull() ? jsonObject.get("verified").getAsBoolean() : false;

            String stripeId = !jsonObject.get("stripeId").isJsonNull() ? jsonObject.get("stripeId").getAsString() : "0";
            String appVersion = !jsonObject.get("appVersion").isJsonNull() ? jsonObject.get("appVersion").getAsString() : "0";
            int transferBalance = !jsonObject.get("transferBalance").isJsonNull() ? jsonObject.get("transferBalance").getAsInt() : 0;
            Boolean paymentFlagged = !jsonObject.get("paymentFlagged").isJsonNull() ? jsonObject.get("paymentFlagged").getAsBoolean() : false;

            Boolean onboarded = !jsonObject.get("onboarded").isJsonNull() ? jsonObject.get("onboarded").getAsBoolean() : false;
            String createdAt = !jsonObject.get("createdAt").isJsonNull() ? jsonObject.get("createdAt").getAsString() : "0";
            String updatedAt = !jsonObject.get("updatedAt").isJsonNull() ? jsonObject.get("updatedAt").getAsString() : "0";
            String socialLinks = !jsonObject.get("socialLinks").isJsonNull() ? jsonObject.get("socialLinks").getAsJsonObject().toString() : "0";

            String from = !(jsonObject.get("from") == null || jsonObject.get("from").isJsonNull()) ? jsonObject.get("from").getAsString() : "0";

            return new UserEntity(0, id, userId, from,
                    payerBalance, receiverBalance, available,
                    contacts, favorites, history,
                    contributors, sponsors,
                    name,
                    socketId, firebaseDeviceToken,
                    payerEmail, images, coverImage,
                    profileImage, wsSocketId, onboardingLink, username, verificationCode,
                    verified, stripeId, appVersion, transferBalance, paymentFlagged, onboarded, createdAt, updatedAt, socialLinks);

        /*return new User(it.getPk(), it.getId(), it.getUserId(), it.getPayerBalance(),
                it.getBalance(), it.getAvailable(), it.getContacts(), it.getFavorites(),
                it.getHistory(), it.getContributors(), it.getSponsors(), it.getReceiverBalance(),
                it.getEmail(), it.getName(), it.getPaypal(), it.getSocketId(),
                it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
                it.getProfileImage());*/

        }catch(Exception e){
            Log.e("TIME123", "Error Exception: "+e.getMessage().toString());
        }

        return null;

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

        return new UserEntity(it.getPk(), it.getId(), it.getUserId(), it.getFrom(), it.getPayerBalance(), it.getReceiverBalance(),
                 it.getAvailable(), it.getContacts(), it.getFavorites(),
                it.getHistory(), it.getContributors(), it.getSponsors(),
                it.getName(), it.getSocketId(),
                it.getFirebaseDeviceToken(), it.getPayerEmail(), it.getImages(), it.getCoverImage(),
                it.getProfileImage(), it.getWsSocketId(), it.getOnboardingLink(), it.getUsername(), it.getVerificationCode(),
                it.getVerified(), it.getStripeId(), it.getAppVersion(), it.getTransferBalance(), it.getPaymentFlagged(),
                it.getOnboarded(), it.getCreatedAt(), it.getUpdatedAt(), it.getSocialLinks());


    }

}