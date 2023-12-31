package com.dhandev.rekapin.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_table WHERE dateInMillis >= :fromDateInMillis ORDER BY dateInMillis DESC")
    fun getAllTransaction(fromDateInMillis: Long): Flow<List<TransactionItemModel>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TransactionItemModel)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<TransactionItemModel>)
    @Delete
    suspend fun delete(item: TransactionItemModel)
    @Update
    suspend fun updateTransaction(item: TransactionItemModel)
    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 1 AND dateInMillis >= :fromDateInMillis")
    fun getTotalExpenses(fromDateInMillis: Long): Flow<Double>

    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 0  AND dateInMillis >= :fromDateInMillis")
    fun getTotalIncome(fromDateInMillis: Long): Flow<Double>

    @Query("DELETE FROM transaction_table")
    fun clearTable()
}