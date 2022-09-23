package org.example.domain.getter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Book {

    private final String name;
    private final Integer isbn;

    public String getName() {
        return name;
    }

    public Integer getIsbn() {
        return isbn;
    }

    // if there are more than two getter...
    public String getName2(){
        return name;
    }
}
