package com.theironyard.Controllers;
import com.theironyard.Command.QuoteCommand;
import com.theironyard.Command.TagCommand;
import com.theironyard.Entities.Quote;
import com.theironyard.Entities.Tag;
import com.theironyard.Entities.User;
import com.theironyard.Services.QuoteRepository;
import com.theironyard.Services.TagRepository;
import com.theironyard.Services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel on 8/1/16.
 */
@RestController
public class IronQuotesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    TagRepository tagRepository;

    /*@RequestMapping(path = "/user", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user, BindingResult bindingResult,
                          HttpServletResponse response){

        if(bindingResult.hasErrors()){
            response.setStatus(400);
            return null;
        }

        userRepository.save(user);
        return user;
    }*/


    //*********CREATE-PATHS*********//
    @RequestMapping(path = "/quotes", method = RequestMethod.POST)
    public Quote createQuote(@RequestBody Quote quote){
        quoteRepository.save(quote);

        return quote;
    }

    @RequestMapping(path = "/quotes/{id}/tags", method = RequestMethod.POST)
    public Quote createTag(@PathVariable int id, @RequestBody TagCommand command) throws Exception {
        Quote quote = quoteRepository.getOne(id);

        if(quote == null){
            throw new Exception("Quote doesn't exist");
        }

        Tag tag = tagRepository.findFirstByValue(command.getValue());
        if(tag == null){
            tag = new Tag(command.getValue());
            tagRepository.save(tag);
        }

        quote.addTag(tag);
        quoteRepository.save(quote);
        return quote;
    }

    //*********READ-PATHS*********//
    @RequestMapping(path = "/quotes/{id}", method = RequestMethod.GET)
    public Quote getQuote(@PathVariable int id) throws Exception {
        Quote quote = quoteRepository.getOne(id);

        if (quote == null){
            throw new Exception("Quote doesn't exist");
        }

        return quote;
    }

    @RequestMapping(path = "/tags/{id}", method = RequestMethod.GET)
    public Tag getTag(@PathVariable int id) throws Exception {
        Tag tag = tagRepository.getOne(id);

        if (tag  == null){
            throw new Exception("Tag doesn't exit");
        }

        return tag;
    }

    //*********UPDATE-PATHS*********//
    @RequestMapping(path = "/quotes/{id}", method = RequestMethod.PUT)
    public Quote updateQuote(@PathVariable int id, @RequestBody QuoteCommand command) throws Exception {
        Quote quote = quoteRepository.getOne(id);

        if (quote == null){
            throw new Exception("Quote doesn't exist");
        }

        quote.setQuote(command.getQuote());

        quoteRepository.save(quote);

        return quote;
    }

    @RequestMapping(path = "/quotes/{quoteId}/tags/{tagId}", method = RequestMethod.PUT)
    public Tag updateTag(@PathVariable int quoteId, @PathVariable int tagId, @RequestBody TagCommand command) throws Exception {
        Quote quote = quoteRepository.getOne(quoteId);

        if (quote == null){
            throw new Exception("Quote doesn't exist");
        }

        Tag tag = quote.editTag(tagId, command.getValue());
        if (tag == null){
            throw new Exception("There is no such tag for this Quote");
        }

        return tag;
    }

    //*********DELETE-PATHS*********//
    @RequestMapping(path = "/quotes/{id}", method = RequestMethod.DELETE)
    public List<Quote> deleteQuote(@PathVariable int id) throws Exception {
        Quote quote = quoteRepository.getOne(id);

        if (quote == null){
            throw new Exception("Quote doesn't exist");
        }else {
            quoteRepository.delete(quote);
        }
        return quoteRepository.findAll();
    }

    @RequestMapping(path = "/quotes/{quoteId}/tags/{tagId}", method = RequestMethod.DELETE)
    public Quote deleteTag(@PathVariable int tagId, @PathVariable int quoteId) throws Exception {
        Tag tag = tagRepository.getOne(tagId);
        Quote quote = quoteRepository.getOne(quoteId);
        if (tag == null){
            throw new Exception("Tag doesn't exist");
        }else{
            quote.deleteTag(tag);
            quoteRepository.save(quote);
        }

        return quote;
    }


    //*********LIST-PATHS*********//
    @RequestMapping(path = "/quotes", method = RequestMethod.GET)
    public List<Quote> getQuotes(){
        return quoteRepository.findAll();
    }

    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public List<Tag> getTags(){
        return tagRepository.findAll();
    }
}