package com.pidzama.smokecrafthookahapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Tobaccos")
    fun getListArchiveTobaccos(): List<RandomRecipeSubListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeToDataBase(recipe: List<RandomRecipeSubListItem>)
}