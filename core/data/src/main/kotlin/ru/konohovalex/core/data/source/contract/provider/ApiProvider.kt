package ru.konohovalex.core.data.source.contract.provider

import ru.konohovalex.core.data.source.contract.Api

interface ApiProvider<P, A: Api> : Provider<P, A>
