package com.demo.libraryProject.api;

import com.demo.libraryProject.domain.BookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/jws/books")
@Slf4j
@RequiredArgsConstructor
@Api(value = "library", description = "Operations that are part of BookJws Controller")
public class BookJwsController {

    public final JWSUtil jwsUtil;
    private final BookControllerRestApi bookControllerRestApi;

    @GetMapping("")
    @ApiOperation(value = "View jws for retrieved books", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "The resource is forbidden."),
            @ApiResponse(code = 404, message = "The resource is not found.")
    })
    public String getListBooksJson() {
        String signedJson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<BookDto> bookList = bookControllerRestApi.getAllBooks();
            List<Map<String, Object>> mapList = mapper.convertValue(bookList, new TypeReference<List<Map<String, Object>>>() {
            });
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(mapList);
            JSONObject object = new JSONObject();
            object.put("bookList", jsonArray);
            signedJson = jwsUtil.signServer(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedJson;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    @ApiOperation(value = "Add book to the library.", response = Iterable.class)
    public String addBook(@RequestBody String encodedJson) throws Exception {
        String signedJson = null;
        JSONObject jsonObject = jwsUtil.serializeJWSObjectServer(encodedJson);
        ObjectMapper mapper = new ObjectMapper();
        BookDto bookDto = null;
        try {
            bookDto = mapper.readValue(jsonObject.toJSONString(), new TypeReference<BookDto>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookDto bookCreated = bookControllerRestApi.addBook(bookDto);

        Map<String, Object> map = mapper.convertValue(bookCreated,
                new TypeReference<Map<String, Object>>() {
                });
        JSONObject object = new JSONObject(map);
        try {
            signedJson = jwsUtil.signServer(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedJson;
    }
}
