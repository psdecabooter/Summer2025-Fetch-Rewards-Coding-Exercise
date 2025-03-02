package com.example.fetchrewardscodingexercise

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Tests to validate the functionality of the data formatter class
 */
class DataFormatterUnitTests {

    /**
     * The filtered data is invalid if:
     * There exists any name fields which are null or ""
     */
    @Test
    fun validateFilterData() {
        // 3 types of modals: "" Invalid, null Invalid, and valid
        val invalidModal1 = HiringListModal(1, 2, "");
        val invalidModal2 = HiringListModal(1, 2, null);
        val validModal = HiringListModal(1, 2, "test");
        // Modal which will be changed
        val testModalList: MutableList<HiringListModal> = ArrayList();
        // Target for the filter to look like
        val targetModalList: MutableList<HiringListModal> = ArrayList();
        // Adding elements to modals
        for (i in 1..5) {
            testModalList.add(invalidModal1)
            testModalList.add(invalidModal2)
            testModalList.add(validModal)
            targetModalList.add(validModal)
        }
        // Apply filter to test
        val resultModalList = DataFormatter.filterData(testModalList);

        // Check that the result of the filter matches the target
        assertEquals(targetModalList, resultModalList);
    }

    /**
     * The sorted data is valid if:
     * data is in order by listId first, and the number in the name second
     */
    @Test
    fun validateSortData() {
        // Modals of different precedence
        val modal1 = HiringListModal(1, 1, "Item 14");
        val modal2 = HiringListModal(1, 1, "Item 134");
        val modal3 = HiringListModal(1, 2, "Item 1");
        val modal4 = HiringListModal(1, 3, "Item 29");
        val modal5 = HiringListModal(1, 3, "Item 3942");

        // Create test list
        val testList: MutableList<HiringListModal> = ArrayList()
        testList.add(modal2);
        testList.add(modal3);
        testList.add(modal1);
        testList.add(modal5);
        testList.add(modal4);

        // Create target list
        val targetList: MutableList<HiringListModal> = ArrayList();
        targetList.add(modal1);
        targetList.add(modal2);
        targetList.add(modal3);
        targetList.add(modal4);
        targetList.add(modal5);

        // Apply sort to test
        val resultList = DataFormatter.sortData(testList);

        // Compare result list to target list
        assertEquals(targetList, resultList);
    }

    /**
     * The conversion from hiring name to number is valid if:
     * It returns the number if the name follows the "Item x" format
     * returns 0 otherwise
     */
    @Test
    fun validateHiringNameToInt() {
        // Check that it returns the number for a single digit
        assertEquals(DataFormatter.hiringNameToInt("Item 1"), 1);
        // Check that it returns the number for two digits
        assertEquals(DataFormatter.hiringNameToInt("Item 12"), 12);
        // Check that it returns the number for three digits
        assertEquals(DataFormatter.hiringNameToInt("Item 123"), 123);
        // Check that it returns the number for four digits
        assertEquals(DataFormatter.hiringNameToInt("Item 1234"), 1234);

        // Check that it returns 0 for null
        assertEquals(DataFormatter.hiringNameToInt(null), 0);
        // Check that it returns 0 for a string that doesn't follow the format
        assertEquals(DataFormatter.hiringNameToInt("test"), 0);
    }
}