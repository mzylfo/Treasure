package com.example.treasure.source.event.com.unimib.worldnews.util;

import android.content.Context;

import com.unimib.worldnews.R;
import com.unimib.worldnews.model.Category;
import com.unimib.worldnews.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int MINIMUM_LENGTH_PASSWORD = 8;

    // Constants for NewsAPI.org
    public static final String FRANCE = "fr";
    public static final String ITALY = "it";
    public static final String GERMANY = "de";
    public static final String UNITED_KINGDOM = "gb";
    public static final String SPAIN = "es";

    public static final String BUSINESS = "business";
    public static final String ENTERTAINMENT = "entertainment";
    public static final String GENERAL = "general";
    public static final String HEALTH = "health";
    public static final String SCIENCE = "science";
    public static final String SPORTS = "sports";
    public static final String TECHNOLOGY = "technology";

    public static final List<String> CATEGORIES = Arrays.asList(BUSINESS,
            ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY);

    public static final List<Integer> CATEGORIES_NAMES = Arrays.asList(
            R.string.category_business, R.string.category_entertainment, R.string.category_general,
            R.string.category_health, R.string.category_science, R.string.category_sports, R.string.category_technology);


    public static final List<Integer> CATEGORIES_DRAWABLES = Arrays.asList(R.drawable.category_business,
            R.drawable.category_entertainment, R.drawable.category_general, R.drawable.category_health,
            R.drawable.category_science, R.drawable.category_sports, R.drawable.category_technology);

    public static final List<String> COUNTRIES = Arrays.asList(
            FRANCE, ITALY, GERMANY, SPAIN, UNITED_KINGDOM);

    public static final List<Integer> COUNTRIES_NAMES = Arrays.asList(
            R.string.countries_france, R.string.countries_italy, R.string.countries_germany,
            R.string.countries_spain, R.string.countries_unitedkingdom);

    public static final List<Integer> COUNTRIES_DRAWABLES = Arrays.asList(R.drawable.country_france,
            R.drawable.country_italy, R.drawable.country_germany, R.drawable.country_spain,
            R.drawable.country_unitedkingdom);

    public static ArrayList<Country> generateCountryList(Context context) {
        ArrayList<Country> countriesList = new ArrayList<>();
        for (int i = 0; i < COUNTRIES.size(); i++) {
            countriesList.add(new Country(
                    context.getString(COUNTRIES_NAMES.get(i)),
                    COUNTRIES.get(i),
                    context.getDrawable(COUNTRIES_DRAWABLES.get(i))
            ));
        }
        return countriesList;
    }

    public static ArrayList<Category> generateCategoriesList(Context context) {
        ArrayList<Category> categoriesList = new ArrayList<>();
        for (int i = 0; i < CATEGORIES.size(); i++) {
            categoriesList.add(new Category(
                    context.getString(CATEGORIES_NAMES.get(i)),
                    CATEGORIES.get(i),
                    context.getDrawable(CATEGORIES_DRAWABLES.get(i))
            ));
        }
        return categoriesList;
    }

    public static final String SHARED_PREFERENCES_FILENAME = "com.unimib.worldnews.preferences";
    public static final String SHARED_PREFERENCES_COUNTRY_OF_INTEREST = "country_of_interest";
    public static final String SHARED_PREFERENCES_CATEGORIES_OF_INTEREST = "categories_of_interest";
    public static final String SHARED_PREFERNECES_LAST_UPDATE = "last_update";

    public static final String SAMPLE_JSON_FILENAME = "sample_api_response.json";

    public static final int DATABASE_VERSION = 1;
    public static final String SAVED_ARTICLES_DATABASE = "saved_db";

    public static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    public static final String TOP_HEADLINES_ENDPOINT = "top-headlines";
    public static final String TOP_HEADLINES_COUNTRY_PARAMETER = "country";
    public static final String TOP_HEADLINES_PAGE_SIZE_PARAMETER = "pageSize";
    public static final int TOP_HEADLINES_PAGE_SIZE_VALUE = 100;

    public static final String BUNDLE_KEY_CURRENT_ARTICLE = "current_article";

    public static final int FRESH_TIMEOUT = 1000 * 60; // 1 minute in milliseconds

    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    public static final String FIREBASE_REALTIME_DATABASE = "https://pilotpdm-b7cfd-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_FAVORITE_NEWS_COLLECTION = "favorite_news";
}

