rootProject.name = "MachineNotes"
include(
    ":app",

    ":core:data",
    ":core:design",
    ":core:presentation",
    ":core:ui",
    ":core:utils",

    ":feature:account:data",
    ":feature:account:domain",
    ":feature:account:presentation",

    ":feature:notes:data",
    ":feature:notes:domain",
    ":feature:notes:presentation",

    ":feature:preferences:data",
    ":feature:preferences:domain",
    ":feature:preferences:presentation",
)
