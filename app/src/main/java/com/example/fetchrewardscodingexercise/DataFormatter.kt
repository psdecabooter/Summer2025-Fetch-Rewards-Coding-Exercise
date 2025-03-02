package com.example.fetchrewardscodingexercise

/**
 * A static class to format data
 *
 * This class is used to format the JSON data from fetch to this format:
 *  - Sort the results first by "listId" then by "name" when displaying.
 *  - Filter out any items where "name" is blank or null.
 */
class DataFormatter {
    companion object {

        /**
         * Filters out any items where "name" is blank or null
         *
         * @param hiringData the hiring data which will be filtered
         * @return the filtered hiring data
         */
        fun filterData(hiringData: List<HiringListModal>): List<HiringListModal> {
            return hiringData.filter { it.name != "" && it.name != null }
        }

        /**
         * Sorts the results first by "listID" then by "name"
         *
         * @param hiringData the hiring data which will be filtered
         * @return the filtered hiring data
         */
        fun sortData(hiringData: List<HiringListModal>): List<HiringListModal> {
            return hiringData.sortedWith(compareBy<HiringListModal> { it.listId }.thenBy {
                hiringNameToInt(
                    it.name
                )
            });
        }

        /**
         * Turns the name of the hiring item to a number
         *
         * This is being done because each name is "Item xxx."
         * However, if we sort it normally, Item 280 is before Item 290.
         * Thus we need to convert it to just it's number
         *
         * @param name the name from the hiring item
         * @return the number from "Item xxx" or 0 if the name doesn't follow that format
         */
        fun hiringNameToInt(name: String?): Int {
            // Catch exceptional conditions
            if (name == null || !name.startsWith("Item ")) return 0;
            // Return the part of the string after "Item " converted to an int
            return name.substring(startIndex = 5).toInt();
        }

    }
}