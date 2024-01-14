package com.trsolutions.plugins

import com.trsolutions.configurations.database.PostgresConfiguration
import com.trsolutions.configurations.database.sql.InitialSQL
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
fun configureDatabases() {

   PostgresConfiguration.connect()

   GlobalScope.launch {
      InitialSQL().create("trsolutions")
   }
}
