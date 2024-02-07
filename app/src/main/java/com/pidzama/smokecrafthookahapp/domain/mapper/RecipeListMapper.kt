package com.pidzama.smokecrafthookahapp.domain.mapper

import com.pidzama.smokecrafthookahapp.data.common.base.Mapper
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import javax.inject.Inject

class RecipeListMapper @Inject constructor() :
    Mapper<RandomRecipeSubList, List<RandomRecipeSubListItem?>> {


    override fun fromMap(from: RandomRecipeSubList): List<RandomRecipeSubListItem?> {
        return from.map {
            RandomRecipeSubListItem(
                best_before_date = it.best_before_date,
                brand = it.brand,
                created = it.created,
                id = it.id,
                is_active = it.is_active,
                organization_id = it.organization_id,
                price = it.price,
                purchase_date = it.purchase_date,
                supplier = it.supplier,
                taste = it.taste,
                taste_group = it.taste_group,
                updated = it.updated,
                weight = it.weight
            )
        }
    }


}