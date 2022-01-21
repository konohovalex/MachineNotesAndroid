rootProject.name = "MachineNotes"
include(
    ":app",

    ":core:data",
    ":core:design",
    ":core:presentation",
    ":core:ui",
    ":core:utils",

    ":feature:notes:data",
    ":feature:notes:domain",
    ":feature:notes:presentation",

    ":feature:preferences:data",
    ":feature:preferences:domain",
    ":feature:preferences:presentation",

    ":feature:profile:data",
)
