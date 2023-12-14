package me.ayitinya.grenes.data.users

import kotlinx.datetime.Clock
import me.ayitinya.grenes.auth.Hashers.getHexDigest
import me.ayitinya.grenes.data.Db
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class DefaultUserDao : UserDao {

    override suspend fun allUsers(): List<User> = Db.query {
        UsersTable.selectAll().map {
            User(
                uid = it[UsersTable.uid],
                displayName = it[UsersTable.displayName],
                email = it[UsersTable.email],
                createdAt = it[UsersTable.createdAt],
                location = null
            )
        }
    }

    override suspend fun updateUser(uid: String, user: User) {
        Db.query {
            UsersTable.select { UsersTable.uid eq uid }.distinct().firstOrNull().let {
                if (it != null) {
                    UsersTable.update({ UsersTable.uid eq uid }) { updateStatement ->
                        updateStatement[displayName] = user.displayName
                    }
                } else {
                    UsersTable.insert { insertStatement ->
                        insertStatement[UsersTable.uid] = uid
                        insertStatement[displayName] = user.displayName
                    }
                }
            }
        }
    }

    override suspend fun createNewUserWithUidAndEmail(uid: String, email: String) {
        Db.query {
            UsersTable.insert {
                it[UsersTable.email] = email
                it[UsersTable.uid] = uid
                it[createdAt] = Clock.System.now()
            }
        }
    }


    override suspend fun createNewUserWithEmailAndPassword(
        email: String, password: String
    ) {
        Db.query {

            UsersTable.insert {
                it[UsersTable.email] = email
                it[UsersTable.password] = getHexDigest(password)
                it[createdAt] = Clock.System.now()
            }
        }
    }

    override suspend fun deleteUser(uid: String) {
        Db.query {
            UsersTable.deleteWhere { UsersTable.uid eq uid }
        }
    }

    override suspend fun getUserByEmail(email: String): User? = Db.query {
        return@query UsersTable.select { UsersTable.email eq email }.firstOrNull()?.let {
            User(
                uid = it[UsersTable.uid],
                displayName = it[UsersTable.displayName],
                email = it[UsersTable.email],
                createdAt = it[UsersTable.createdAt],
                location = null
            )
        }
    }

    override suspend fun getUserById(uid: String): User? = Db.query {
        UsersTable.select { UsersTable.uid eq uid }.firstOrNull()?.let {
            User(
                uid = it[UsersTable.uid],
                displayName = it[UsersTable.displayName],
                email = it[UsersTable.email],
                createdAt = it[UsersTable.createdAt],
                location = null
            )
        }
    }
}