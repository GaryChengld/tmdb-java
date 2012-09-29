/*
 * Copyright 2012 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.uwetrottmann.tmdb.services;

import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb.TmdbApiBuilder;
import com.uwetrottmann.tmdb.TmdbApiService;
import com.uwetrottmann.tmdb.entities.Movie;
import com.uwetrottmann.tmdb.entities.ResultsPage;
import com.uwetrottmann.tmdb.entities.Trailers;

public class MoviesService extends TmdbApiService {

    /**
     * Get the basic movie information for a specific movie id.
     * 
     * @param id TMDb id.
     * @return Builder instance.
     */
    public SummaryBuilder summary(Integer id) {
        return new SummaryBuilder(this, id);
    }

    /**
     * Get the basic movie information for a specific movie id.
     * 
     * @param id TMDb id.
     * @param langCode ISO 639-1 code.
     * @return Builder instance.
     */
    public SummaryBuilder summary(Integer id, String langCode) {
        return new SummaryBuilder(this, id).language(langCode);
    }

    /**
     * Get the trailers for a specific movie id.
     * 
     * @param id TMDb id.
     * @return Builder instance.
     */
    public TrailerBuilder trailers(Integer id) {
        return new TrailerBuilder(this, id);
    }

    /**
     * Get the list of popular movies on The Movie Database. This list refreshes
     * every day.
     * 
     * @return Builder instance.
     */
    public PopularBuilder popular() {
        return new PopularBuilder(this);
    }

    /**
     * Get the list of popular movies on The Movie Database. This list refreshes
     * every day.
     * 
     * @param langCode ISO 639-1 code.
     * @return Builder instance.
     */
    public PopularBuilder popular(String langCode) {
        return new PopularBuilder(this).language(langCode);
    }

    public static final class SummaryBuilder extends TmdbApiBuilder<Movie> {
        private static final String URI = "/movie/" + FIELD_ID + FIELD_API_KEY + FIELD_LANGUAGE;

        private SummaryBuilder(MoviesService service, Integer id) {
            super(service, new TypeToken<Movie>() {
            }, URI);

            field(FIELD_ID, id);
        }

        /**
         * Set the language. Attention: will not default to English, but instead
         * will return empty field.
         * 
         * @param languageCode ISO 639-1 code.
         */
        public SummaryBuilder language(String languageCode) {
            parameter(FIELD_LANGUAGE, languageCode);
            return this;
        }
    }

    public static final class TrailerBuilder extends TmdbApiBuilder<Trailers> {
        private static final String URI = "/movie/" + FIELD_ID + "/trailers" + FIELD_API_KEY;

        private TrailerBuilder(MoviesService service, Integer id) {
            super(service, new TypeToken<Trailers>() {
            }, URI);

            field(FIELD_ID, id);
        }
    }

    public static final class PopularBuilder extends TmdbApiBuilder<ResultsPage> {
        private static final String URI = "/movie/popular" + FIELD_API_KEY + FIELD_PAGE
                + FIELD_LANGUAGE;

        private PopularBuilder(MoviesService service) {
            super(service, new TypeToken<ResultsPage>() {
            }, URI);
        }

        private PopularBuilder(MoviesService service, int page) {
            this(service);

            parameter(FIELD_PAGE, page);
        }

        /**
         * Set the language. Attention: will not default to English, but instead
         * will return empty field.
         * 
         * @param languageCode ISO 639-1 code.
         */
        public PopularBuilder language(String languageCode) {
            parameter(FIELD_LANGUAGE, languageCode);
            return this;
        }
    }
}
