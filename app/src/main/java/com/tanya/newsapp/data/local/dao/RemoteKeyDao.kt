package com.tanya.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanya.newsapp.data.local.entities.RemoteKeys

@Dao
interface RemoteKeyDao {

    @Query("SELECT * from RemoteKeys where id=:id and category = :category" +
            " and categoryValue = :categoryValue")
    suspend fun getRemoteKeys (id: String, category: String, categoryValue : String) : RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE from RemoteKeys")
    suspend fun deleteAllRemoteKeys()
}