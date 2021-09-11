package com.example.santanderdioproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(taskEntry: TaskEntry)

    @Delete
    suspend fun delete(taskEntry: TaskEntry)

    @Update
    suspend fun update(taskEntry: TaskEntry)

    @Query(value = "DELETE FROM task_table")
    suspend fun deleteAll()

    @Query(value = "SELECT * FROM TASK_TABLE ORDER BY timestamp DESC")
    fun getAllTasks(): LiveData<List<TaskEntry>>
}