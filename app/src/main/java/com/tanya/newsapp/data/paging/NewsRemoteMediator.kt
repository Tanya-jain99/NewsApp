package com.tanya.newsapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.local.DataBaseHelper
import com.tanya.newsapp.data.local.entities.ArticleEntity
import com.tanya.newsapp.data.local.entities.RemoteKeys
import com.tanya.newsapp.utils.asEntity
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private var query:  LinkedHashMap<String, String>,
    private val networkService: NetworkService,
    private val dbHelper: DataBaseHelper
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun initialize(): InitializeAction {

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val lastModified = dbHelper.runAsTransaction {
             dbHelper.lastModified()
        }
        return if (System.currentTimeMillis() -
            (lastModified ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleEntity>): MediatorResult {
        return try {
            val categoryType  = query.keys.first()
            val categoryValue  = query.values.first()
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            query["page"] = currentPage.toString()
            val response = networkService.getTopHeadlines(query)
            val endOfPaginationReached = 5 == currentPage

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            dbHelper.runAsTransaction {

                if (loadType == LoadType.REFRESH) {
                    dbHelper.clearArticles()
                    dbHelper.clearRemoteKeys()
                }
                val articles : List<ArticleEntity> = response.articles.map {
                    it.asEntity(categoryType, categoryValue)
                }
                dbHelper.insertArticle(articles)

                val keys = articles.map { article ->
                    RemoteKeys(
                        id = article.title,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        category =  article.category,
                        categoryValue = article.categoryId
                    )
                }
                dbHelper.insertRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        }
        catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { id ->
                dbHelper.getRemoteKeys(id = id, category = query.keys.first(),
                    categoryValue = query.values.first()
                )
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                dbHelper.getRemoteKeys(id = article.title, category = article.categoryId,
                    categoryValue = article.category)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                dbHelper.getRemoteKeys(id = article.title, category = article.category,
                    categoryValue = article.categoryId )
            }
    }
}
