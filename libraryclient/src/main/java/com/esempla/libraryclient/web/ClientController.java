package com.esempla.libraryclient.web;

import com.esempla.libraryclient.domain.Book;
import com.esempla.libraryclient.service.MongoDbService;
import com.esempla.libraryclient.utils.JWSUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final LibraryClient libraryClient;
    private final MongoDbService mongoDbService;
    private final JWSUtil jwsUtil;

    @GetMapping(value = "/booklist")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JSONObject getAllBooksJson(){
        String encodedStr = null;
        JSONObject signedJson = null;
        try{
            encodedStr = libraryClient.getAll();
            return signedJson = jwsUtil.serializeJWSObjectClient(encodedStr);
        } catch (Exception e){

        }finally {
            if(signedJson!=null)
            {
                List<Book> bookDtoList = new ArrayList<>();
                JSONArray ja = (JSONArray) signedJson.get("bookList");
                ObjectMapper mapper = new ObjectMapper();
                List<Book> bookList =new ArrayList<>();
                try{
                    bookList=mapper.readValue(ja.toJSONString(), new TypeReference<List<Book>>() {});
                }catch (IOException e){
                    e.printStackTrace();
                }
                for(Book book:bookList){
                    mongoDbService.saveBook(book);

                }
                }
            }
        return signedJson;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createBookJson")
    public JSONObject createBook(@RequestBody String simpleJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(simpleJson, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(jsonMap);

        String signedJSON = null;
        try {
            signedJSON = jwsUtil.signClient(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (signedJSON == null) {
            throw new Exception("is noll singedJson from encoding");
        }
        String recivedSignedJSON = libraryClient.saveBook(signedJSON);
        JSONObject receivedJSONobject = jwsUtil.serializeJWSObjectClient(recivedSignedJSON);

        Book book = mapper.convertValue(receivedJSONobject, new TypeReference<Book>() {
        });
        JSONObject receivedJSONobjectCreated = mongoDbService.saveBook(book);
        return receivedJSONobjectCreated;
    }

//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @PutMapping(value = "/updateBook")
//    public JSONObject update(@RequestBody String simpleJson) throws Exception {
//        return createBook(simpleJson);
//    }


}
