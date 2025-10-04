package top.suzhelan.ktor.mapper

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import top.suzhelan.ktor.entity.PluginInfo


/**
 * 查询数据库
 */
class PluginInfoRepository() {

    init {
        transaction {
            SchemaUtils.create(PluginInfoTable)
        }
    }

    /**
     * 表映射信息
     */
    object PluginInfoTable : IntIdTable("plugin_info") {
        val name = varchar("name", 255)
        val description = varchar("description", 255)
    }


    suspend fun selectAll(): List<PluginInfo> {
        return suspendTransaction {
            PluginInfoTable.selectAll().map {
                PluginInfo(
                    id = it[PluginInfoTable.id].value,
                    name = it[PluginInfoTable.name],
                    description = it[PluginInfoTable.description]
                )
            }
        }
    }

    suspend fun insert(pluginInfo: PluginInfo) {
        suspendTransaction {
            PluginInfoTable.insert {
                it[name] = pluginInfo.name
                it[PluginInfoTable.description] = pluginInfo.description
            }
        }
    }

    suspend fun update(pluginInfo: PluginInfo) {
        suspendTransaction {
            PluginInfoTable.update({ PluginInfoTable.id.eq(pluginInfo.id) }) {
                it[name] = pluginInfo.name
                it[PluginInfoTable.description] = pluginInfo.description
            }
        }
    }

    suspend fun deleteById(id: Int) {
        suspendTransaction {
            PluginInfoTable.deleteWhere {
                PluginInfoTable.id.eq(id)
            }
        }
    }


    suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
        newSuspendedTransaction(Dispatchers.IO, statement = block)

}