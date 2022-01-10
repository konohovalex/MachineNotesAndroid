rootProject.name = "MachineNotes"
include(
    ":app",

    ":core:data",
    ":core:design",
    ":core:ui",
    ":core:utils",

    ":feature:notes:data",
    ":feature:notes:domain",
    ":feature:notes:presentation",

    ":feature:preferences:data",

    ":feature:profile:data",
)
