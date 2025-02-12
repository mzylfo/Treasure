package com.example.treasure.source.event.com.unimib.worldnews.source.article;


import com.unimib.worldnews.repository.article.ArticleResponseCallback;

/**
 * Base class to get news from a remote source.
 */
public abstract class BaseArticleRemoteDataSource {
    protected ArticleResponseCallback articleCallback;

    public void setArticleCallback(ArticleResponseCallback articleCallback) {
        this.articleCallback = articleCallback;
    }

    public abstract void getArticles(String country);
}

