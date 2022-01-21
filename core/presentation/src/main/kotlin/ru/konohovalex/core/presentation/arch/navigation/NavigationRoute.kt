package ru.konohovalex.core.presentation.arch.navigation

interface NavigationRoute {
    val entryPoint: String
    val destinations: List<String>

    /** Example: "home/users?userId={userId}" */
    fun buildGraphRoute(parameters: List<String>? = null) = buildString {
        buildDestinations(hasParameters = !parameters.isNullOrEmpty())
        parameters?.forEachIndexed { index, parameter ->
            appendParameterAmpersandIfNecessary(
                index = index,
                parametersSize = parameters.size,
            )
            append("$parameter={$parameter}")
        }
    }

    /** Example: "home/users?userId=12345" */
    fun buildNavigationRoute(parameters: List<NavigationParameter<String, String?>>? = null) = buildString {
        buildDestinations(hasParameters = !parameters.isNullOrEmpty())
        parameters?.forEachIndexed { index, entry ->
            appendParameterAmpersandIfNecessary(
                index = index,
                parametersSize = parameters.size,
            )
            append("${entry.key}={${entry.value}}")
        }
    }

    private fun StringBuilder.buildDestinations(hasParameters: Boolean) {
        append(entryPoint)
        destinations.forEach {
            append("/$it")
        }
        if (hasParameters) append("?")
    }

    private fun StringBuilder.appendParameterAmpersandIfNecessary(index: Int, parametersSize: Int) {
        if (index in 1 until parametersSize) append("&")
    }
}
