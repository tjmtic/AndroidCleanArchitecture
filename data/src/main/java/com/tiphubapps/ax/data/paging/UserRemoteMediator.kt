package com.tiphubapps.ax.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tiphubapps.ax.data.api.ApiMainHeadersProvider
import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.model.UserRemoteKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(private val userApi: UserApi, private val userDB: UserDB) :
    RemoteMediator<Int, User>() {

    private val userDao = userDB.userDao()
    private val userRemoteKeysDao = userDB.userRemoteKeysDao()

    val headersProvider = ApiMainHeadersProvider()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            Log.d("TIME123", "REMOTE MEDIATOR - LOGGING FOR GET ALL USERS");
            val response = userApi.getAllUsers(authedHeaders = headersProvider.getAuthenticatedHeaders(""))
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                Log.d("TIME123", "REMOTE MEDIATOR RESPONSE - LOGGING FOR GET ALL USERS " + response);

                Log.d("TIME123", "REMOTE MEDIATOR RESPONSE - LOGGING FOR GET ALL USERS " + responseData);

                /*val rec = responseData?.getAsJsonArray("receivers");
                //val rec2 = rec?.asString;
                val con = responseData?.getAsJsonArray("contributors");
                val spon = responseData?.getAsJsonArray("sponsors");
                val history = responseData?.getAsJsonArray("history");
                val contacts = responseData?.getAsJsonArray("contacts");
                val favorites = responseData?.getAsJsonArray("sponsors");*/

                /*val list: List<User> = mapper.readValue(
                    rec,
                    TypeFactory.defaultInstance().constructCollectionType(
                        MutableList::class.java,
                        User::class.java
                    )
                )*/
               /* val gson = Gson()
                val type: Type = object : TypeToken<List<User?>?>() {}.type
                val userList: List<User> = gson.fromJson(rec2, type)

                Log.d("TIME123", "User lists..:" + userList)*/
               /* Log.d("TIME123", "rec..:" + rec);
                Log.d("TIME123", "con..:" + con);
                Log.d("TIME123", "spon..:" + spon);
                Log.d("TIME123", "history..:" + history);
                Log.d("TIME123", "contacs..:" + contacts);
                Log.d("TIME123", "favorites..:" + favorites);*/


                endOfPaginationReached = true;//responseData == null
                responseData?.let {
                    userDB.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            userDao.deleteAllUsers()
                            userRemoteKeysDao.deleteAllUserRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                      /*  responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.users.map { user ->
                            UserRemoteKeys(
                                id = user.id,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        userRemoteKeysDao.addAllUserRemoteKeys(userRemoteKeys = keys)*/
                      //  userDao.addUsers(users = userList)
                    }
                }

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, User>,
    ): UserRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userRemoteKeysDao.getUserRemoteKeys(userId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, User>,
    ): UserRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                userRemoteKeysDao.getUserRemoteKeys(userId = user.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, User>,
    ): UserRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                userRemoteKeysDao.getUserRemoteKeys(userId = user.id)
            }
    }
}