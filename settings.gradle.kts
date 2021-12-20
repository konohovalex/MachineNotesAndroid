rootProject.name = "MachineNotes"
include(
    ":app",

    ":core:data",
    ":core:design",

    ":feature:api:imagerecognition",
    ":feature:api:navigation",
    ":feature:api:notes",
    ":feature:api:preferences",
    ":feature:api:profile",
    ":feature:api:speechrecognition",

    ":feature:impl:imagerecognition",
    ":feature:impl:navigation",
    ":feature:impl:notes",
    ":feature:impl:preferences",
    ":feature:impl:profile",
    ":feature:impl:speechrecognition",
)
