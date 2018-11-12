package com.esempla.libraryclient.service;

import com.esempla.libraryclient.domain.Book;
import com.esempla.libraryclient.repository.BookRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MongoDbService {
    private final MongoTemplate mongoTemplate;
    private final BookRepo bookRepository;

    public void saveBook(String obj){
        DBObject dbObject =(DBObject) com.mongodb.util.JSON.parse(obj);
        mongoTemplate.insert(dbObject, "book");
    }

    public JSONObject saveBook(Book book){
        return toJSONCOnverter(bookRepository.save(book));
    }

    public JSONArray getAllBooks(){
        return  toJsonArrayConverter(bookRepository.findAll());
    }

//    public JSONObject getById(int id) {
//        return toJSONCOnverter(bookRepository.findOne(id).get());
//    }
//
//    public void deleteBook(int id) {
//        bookRepository.delete(id);
//    }

    private JSONArray toJsonArrayConverter(List<Book> bookDtoList){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(bookDtoList);
        return jsonArray;
    }

    private JSONObject toJSONCOnverter(Book book){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.convertValue(book, new TypeReference<Map<String,Object>>(){});
        JSONObject object = new JSONObject(map);
        return object;
    }
}
