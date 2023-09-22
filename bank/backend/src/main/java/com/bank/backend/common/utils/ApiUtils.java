package com.bank.backend.common.utils;

import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtils {
    public static <T> ApiResult<T> success(T response){
        return new ApiResult<T>(true, response, null);
    }

    public static <T> ApiResult<T> fail(T response){
        return new ApiResult<T>(false, response, null);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status){
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String message, HttpStatus status){
        return new ApiResult<>(false, null, new ApiError(message, status));
    }

    public static class ApiResult<T>{
        private final boolean success;
        private final T response;
        private final ApiError error;

        private ApiResult(boolean success, T response, ApiError error){
            this.success = success;
            this.response = response;
            this.error = error;
        }

        public boolean isSuccess(){
            return success;
        }

        public ApiError getError(){
            return error;
        }

        public T getResponse(){
            return response;
        }
    }

    public static class ApiError{
        private final String message;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status){
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }

        public String getMessage(){
            return message;
        }
        public int getStatus(){
            return status;
        }
    }

    public static String callBankApi(String apiUrl, int type) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(type==0) connection.setRequestMethod("GET");
        else if(type==1) connection.setRequestMethod("POST");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            return null;
        }
    }
}