package io.github.manamiproject.manami.cache


/**
 * The cache holds anime meta data.
 */
interface Cache : AnimeFetcher {

  fun invalidate()
}
