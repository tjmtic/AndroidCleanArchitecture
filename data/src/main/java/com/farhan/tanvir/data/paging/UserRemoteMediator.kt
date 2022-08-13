package com.farhan.tanvir.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.farhan.tanvir.data.BuildConfig
import com.farhan.tanvir.data.api.UserApi
import com.farhan.tanvir.data.db.UserDB
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(private val userApi: UserApi, private val userDB: UserDB) :
    RemoteMediator<Int, User>() {

    private val userDao = userDB.userDao()
    private val userRemoteKeysDao = userDB.userRemoteKeysDao()

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
            val response = userApi.getAllUsers()
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                Log.d("TIME123", "REMOTE MEDIATOR RESPONSE - LOGGING FOR GET ALL USERS " + response);

                Log.d("TIME123", "REMOTE MEDIATOR RESPONSE - LOGGING FOR GET ALL USERS " + responseData);
                endOfPaginationReached = responseData == null
                responseData?.let {
                    userDB.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            userDao.deleteAllUsers()
                            userRemoteKeysDao.deleteAllUserRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                        responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.users.map { user ->
                            UserRemoteKeys(
                                id = user.userId,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        userRemoteKeysDao.addAllUserRemoteKeys(userRemoteKeys = keys)
                        userDao.addUsers(users = responseData.users)
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
            state.closestItemToPosition(position)?.userId?.let { id ->
                userRemoteKeysDao.getUserRemoteKeys(userId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, User>,
    ): UserRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                userRemoteKeysDao.getUserRemoteKeys(userId = user.userId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, User>,
    ): UserRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                userRemoteKeysDao.getUserRemoteKeys(userId = user.userId)
            }
    }
}