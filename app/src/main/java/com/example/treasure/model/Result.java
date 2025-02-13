package com.example.treasure.model;

public abstract class Result {
    // Costruttore privato per impedire l'istanza diretta
    private Result() {}

    // Metodo per verificare se il risultato è un successo
    public boolean isSuccess() {
        return !(this instanceof Error);
    }

    /**
     * Classe che rappresenta un'azione riuscita durante l'interazione
     * con un Web Service o un database locale.
     */
    public static final class Weather extends Result {
        private final com.example.treasure.model.Weather weatherData;

        public Weather(com.example.treasure.model.Weather weatherData) {
            this.weatherData = weatherData;
        }

        public com.example.treasure.model.Weather getWeatherData() {
            return weatherData;
        }
    }

    public static final class User extends Result {
        private final com.example.treasure.model.User userData;

        public User(com.example.treasure.model.User userData) {
            this.userData = userData;
        }

        public com.example.treasure.model.User getUserData() {
            return userData;
        }
    }

    public static final class Event extends Result {
        private final com.example.treasure.model.Event eventResponse;

        public Event(com.example.treasure.model.Event eventResponse) {
            this.eventResponse = eventResponse;
        }

        public com.example.treasure.model.Event getEventResponse() {
            return eventResponse;
        }
    }

    public static final class Feeling extends Result {
        private final com.example.treasure.model.Feeling feelingResponse;

        public Feeling(com.example.treasure.model.Feeling feelingResponse) {
            this.feelingResponse = feelingResponse;
        }

        public com.example.treasure.model.Feeling getFeelingResponse() {
            return feelingResponse;
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