package com.mailwave.api.modules.tags.services;

import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.exceptions.NoRecordsFoundException;
import com.mailwave.api.exceptions.TagNotFoundException;
import com.mailwave.api.modules.accounts.Account;
import com.mailwave.api.modules.accounts.AccountService;
import com.mailwave.api.modules.tags.dtos.tag.TagCreateRequest;
import com.mailwave.api.modules.tags.dtos.tag.TagResponse;
import com.mailwave.api.modules.tags.dtos.tag.TagUpdateRequest;
import com.mailwave.api.modules.tags.models.Tag;
import com.mailwave.api.modules.tags.repositories.TagRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final AccountService accountService;

    public TagService(TagRepository tagRepository, AccountService accountService) {
        this.tagRepository = tagRepository;
        this.accountService = accountService;
    }

    public TagResponse createTag(TagCreateRequest model){

        Tag tag = new Tag();
        tag.setTagName(model.tagName());
        tag.setCreatedAt(LocalDateTime.now());

        Account account = accountService.getAccountById(model.accountId());
        tag.setAccount(account);

        Tag savedTag = tagRepository.save(tag);

        return new TagResponse(savedTag);

    }

    public List<TagResponse> getAllTags() {

        List<TagResponse> tags = new ArrayList<>();

        try{

            List<Tag> results = tagRepository.findAll();

            if(results.isEmpty()) throw new NoRecordsFoundException();

            results.forEach(tag -> tags.add(new TagResponse(tag)));

            return tags;

        }catch (NoRecordsFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }

    public Tag getTagById(Long id){
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    public TagResponse updateTag(TagUpdateRequest model){

        try{

            Tag tag = tagRepository.findById(model.id()).orElseThrow(() -> new TagNotFoundException(model.id()));
            Account account = accountService.getAccountById(model.accountId());

            tag.setTagName(model.tagName());
            tag.setAccount(account);

            Tag updatedTag = tagRepository.save(tag);

            return new TagResponse(updatedTag);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public void deleteTag(Long id){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));

        try{
            tagRepository.delete(tag);
        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }

    }

}
