package com.danielkreitsch.strobo.backend.util

import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import kotlin.math.min

object StringIdGenerator
{
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Get a free id which is generated of the input string.
     * This id is guaranteed to be unique.
     */
    fun generateUniqueId(repository: CrudRepository<out Any, String>, input: String, maxLength: Int? = null): String
    {
        var id = input.toLowerCase().replace(" ", "").replace(Regex("[^A-Za-z0-9 ]"), "")

        if (maxLength != null)
        {
            id = id.substring(0, min(maxLength, id.length))
        }

        val idWithoutExtraNumber = id
        var extraNumber = 1

        while (repository.findById(id).isPresent)
        {
            extraNumber++

            if (maxLength != null)
            {
                if (extraNumber.toString().length > maxLength)
                {
                    throw Exception("Failed to generate a unique id because the maximum length ($maxLength) is too low.")
                }

                id = idWithoutExtraNumber.substring(0, min(maxLength - extraNumber.toString().length, idWithoutExtraNumber.length)) + extraNumber
            }
            else
            {
                id = idWithoutExtraNumber + extraNumber
            }
        }

        return id
    }
}
