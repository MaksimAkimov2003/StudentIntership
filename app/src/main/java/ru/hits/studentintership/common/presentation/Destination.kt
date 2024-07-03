package ru.hits.studentintership.common.presentation

@Suppress("UnnecessaryAbstractClass")
abstract class Destination {
  val dest: String
    get() = javaClass.simpleName

  val route: String by lazy {
    buildString {
      append(dest)
      args().forEach { arg ->
        append("/{$arg}")
      }
      optionalArgs().forEachIndexed { i, arg ->
        append(if (i == 0) "?" else "&")
        append("$arg={$arg}")
      }
    }
  }

  open fun args(): List<String> = emptyList()
  open fun optionalArgs(): List<String> = emptyList()

  fun destWithArgs(vararg args: Any?) = buildString {
    append(dest)
    args.forEach { arg ->
      append("/$arg")
    }
  }

  fun destWithArgs(args: List<Any>, optionalArgs: Map<String, Any?> = emptyMap()) = buildString {
    append(dest)
    args.forEach { arg ->
      append("/$arg")
    }
    var i = 0
    for ((arg, value) in optionalArgs) {
      if (value == null) continue
      append(if (i == 0) "?" else "&")
      append("$arg=$value")
      i++
    }
  }
}
