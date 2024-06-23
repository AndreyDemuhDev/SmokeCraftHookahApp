package com.pidzama.smokecrafthookahapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem

@Database(entities = [RandomRecipeSubListItem::class], version = 1, exportSchema = false)
abstract class RecipeDataBase : RoomDatabase() {
    abstract fun RecipeDao(): RecipeDao
}