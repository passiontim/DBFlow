package com.raizlabs.android.dbflow.structure

import com.raizlabs.android.dbflow.config.modelAdapter
import com.raizlabs.android.dbflow.config.writableDatabaseForTable
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper
import com.raizlabs.android.dbflow.structure.database.transaction.DefaultTransactionQueue

interface Model : ReadOnlyModel {

    /**
     * Saves the object in the DB.
     *
     * @return true if successful
     */
    fun save(): Boolean

    /**
     * Saves the object in the DB.
     *
     * @return true if successful
     */
    fun save(wrapper: DatabaseWrapper): Boolean

    /**
     * Deletes the object in the DB
     *
     * @return true if successful
     */
    fun delete(): Boolean

    /**
     * Deletes the object in the DB
     *
     * @return true if successful
     */
    fun delete(wrapper: DatabaseWrapper): Boolean

    /**
     * Updates an object in the DB. Does not insert on failure.
     *
     * @return true if successful
     */
    fun update(): Boolean

    /**
     * Updates an object in the DB. Does not insert on failure.
     *
     * @return true if successful
     */
    fun update(wrapper: DatabaseWrapper): Boolean

    /**
     * Inserts the object into the DB
     *
     * @return the count of the rows affected, should only be 1 here, or -1 if failed.
     */
    fun insert(): Long

    /**
     * Inserts the object into the DB
     *
     * @return the count of the rows affected, should only be 1 here, or -1 if failed.
     */
    fun insert(wrapper: DatabaseWrapper): Long

    /**
     * @return An async instance of this model where all transactions are on the [DefaultTransactionQueue]
     */
    @Deprecated("Use Transactions and extensions.")
    fun async(): AsyncModel<out Model>

    companion object {

        /**
         * Returned when [.insert] occurs in an async state or some kind of issue occurs.
         */
        const val INVALID_ROW_ID: Long = -1
    }

}

inline fun <reified T : Any> T.save(databaseWrapper: DatabaseWrapper = writableDatabaseForTable<T>()) = modelAdapter<T>().save(this, databaseWrapper)

inline fun <reified T : Any> T.insert(databaseWrapper: DatabaseWrapper = writableDatabaseForTable<T>()) = modelAdapter<T>().insert(this, databaseWrapper)

inline fun <reified T : Any> T.update(databaseWrapper: DatabaseWrapper = writableDatabaseForTable<T>()) = modelAdapter<T>().update(this, databaseWrapper)

inline fun <reified T : Any> T.delete(databaseWrapper: DatabaseWrapper = writableDatabaseForTable<T>()) = modelAdapter<T>().delete(this, databaseWrapper)

inline fun <reified T : Any> T.exists(databaseWrapper: DatabaseWrapper = writableDatabaseForTable<T>()) = modelAdapter<T>().exists(this, databaseWrapper)

inline fun <reified T : Any> T.async() = AsyncModel(this)