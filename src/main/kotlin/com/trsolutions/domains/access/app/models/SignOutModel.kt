package com.trsolutions.domains.access.app.models

import com.trsolutions.configurations.commons.app.models.interfaces.IModel
import com.trsolutions.configurations.serializers.KUUID
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class SignOutModel(
   @Serializable(KUUID::class)
   val id: UUID
) : IModel {

   override fun validate(): List<String> = listOfNotNull()
}
