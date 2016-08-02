package com.theironyard.controllers;

import com.theironyard.command.QuoteCommand;
import com.theironyard.command.TagCommand;
import com.theironyard.entities.Quote;
import com.theironyard.entities.Tag;
import com.theironyard.services.QuoteRepository;
import com.theironyard.services.TagRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


/**
 * Created by EddyJ on 8/1/16.
 */
@RestController
public class IronQuotesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    TagRepository tagRepository;

    @RequestMapping(path = "/quotes", method = RequestMethod.POST)
    public Quote createQuote(@RequestBody Quote quote){
        quoteRepository.save(quote);
        return quote;
    }

    @RequestMapping(path = "/quotes",method = RequestMethod.GET)
    public List<Quote> getAllQuotes(){
        List<Quote> quoteList = quoteRepository.findAll();
        return quoteList;
    }

    @RequestMapping(path = "/quotes/{id}",method = RequestMethod.GET)
    public Quote getQuote(@PathVariable Integer id){
        Quote quote = quoteRepository.findOne(id);
        return quote;
    }

    @RequestMapping(path = "/quotes/{id}",method = RequestMethod.PUT)
    public Quote updateQuote(@PathVariable Integer id, @RequestBody QuoteCommand quoteCommand){
        Quote quote = quoteRepository.findOne(id);
        quote.setText(quoteCommand.getText());
        quote.setAuthor(quoteCommand.getAuthor());
        quoteRepository.save(quote);
        return quote;
    }

    @RequestMapping(path = "/quotes/{id}",method = RequestMethod.DELETE)
    public void deleteQuote(@PathVariable Integer id) throws Exception {
        Quote deletedQuote = quoteRepository.findOne(id);
        if(deletedQuote == null){
            throw new Exception("Quote does not exist");
        }
        quoteRepository.delete(deletedQuote);
    }

    @RequestMapping(path = "/tags",method = RequestMethod.GET)
    public List<Tag> getAllTag(){
        List<Tag> tagList = tagRepository.findAll();
        return tagList;
    }

    @RequestMapping(path = "/tags/{id}",method = RequestMethod.GET)
    public Tag getTag(@PathVariable Integer id){
        Tag tag = tagRepository.findOne(id);
        return tag;
    }

    @RequestMapping(path = "/quotes/{id}/tags",method = RequestMethod.POST)
    public Tag addTag(@PathVariable Integer id, @RequestBody TagCommand tagcommand){
        Quote quote = quoteRepository.findOne(id);
        Tag tag = tagRepository.findFirstByValue(tagcommand.getValue());
        if (tag == null){
            tag = new Tag(tagcommand.getValue());
            tagRepository.save(tag);
        }
        quote.addTag(tag);
        tagRepository.save(tag);
        return tag;
    }

    @RequestMapping(path = "/quotes/{quoteId}/tags/{tagId}",method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable Integer quoteId, @PathVariable Integer tagId){
        Quote quote = quoteRepository.findOne(quoteId);
        quote.deleteTag(tagRepository.getOne(tagId));
        quoteRepository.save(quote);
    }

    @RequestMapping(path = "/quotes/{quoteId}/tags/{tagId}",method = RequestMethod.PUT)
    public Tag updateTag(@PathVariable Integer quoteId, @PathVariable Integer tagId ,@RequestBody TagCommand tagComannd){
        Quote quote = quoteRepository.findOne(quoteId);
        Tag tag = quote.editTag(tagId, tagComannd.getValue());
        tagRepository.save(tag);
        return tag;
    }
}
