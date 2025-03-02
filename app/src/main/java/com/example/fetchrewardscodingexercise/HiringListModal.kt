package com.example.fetchrewardscodingexercise

/**
 * HiringListModal defines the structure of a hiring list item
 * Will be used for getting api data
 *
 * @param id the id of the item
 * @param listId the group id of the item
 * @param name the name of the item, sometimes is given as null
 */
data class HiringListModal(
    val id: Int,
    val listId: Int,
    val name: String?
)