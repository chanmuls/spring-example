rootProject.name = "spring-example"

include(
    "application:api",
    "application:consumer",
    "application:web",
    "application:batch",

    "domain",

    "client:redis-client",

    "facade",
    "core"
)
