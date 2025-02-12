package com.example.treasure.model;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return !(this instanceof Error);
    }

    /**
     * Class that represents a successful action during the interaction
     * with a Web Service or a local database.
     */
    public static final class Weather extends Result {
        private final com.example.treasure.model.Weather weatherData;
        public Weather(com.example.treasure.model.Weather weatherData) {
            this.weatherData = weatherData;
        }

        public com.example.treasure.model.Weather getData() {
            return weatherData;
        }
    }

    public static final class Event extends Result {
        private final com.example.treasure.model.Event eventResponse;
        public Event(com.example.treasure.model.Event eventResponse) {
            this.eventResponse = eventResponse;
        }
        public com.example.treasure.model.Event getData() {
            return eventResponse;
        }
    }

    public static final class Feeling extends Result {
        private final com.example.treasure.model.Feeling feelingResponse;
        public Feeling(com.example.treasure.model.Feeling feelingResponse) {
            this.feelingResponse = feelingResponse;
        }
        public com.example.treasure.model.Feeling getData() {
            return feelingResponse;
        }
    }

    public static final class UserSuccess extends Result {
        private final User user;
        public UserSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
        }
    }



    /**
     * Class that represents an error occurred during the interaction
     * with a Web Service or a local database.
     */
    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}