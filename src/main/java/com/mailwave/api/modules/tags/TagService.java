package com.mailwave.api.modules.tags;

import com.mailwave.api.exceptions.*;
import com.mailwave.api.modules.accounts.AccountRepository;
import com.mailwave.api.modules.tags.dtos.TagCreateRequest;
import com.mailwave.api.modules.tags.dtos.TagResponse;
import com.mailwave.api.modules.tags.dtos.TagUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final AccountRepository accountRepository;

    public TagService(TagRepository tagRepository, AccountRepository accountRepository) {
        this.tagRepository = tagRepository;
        this.accountRepository = accountRepository;
    }

    public TagResponse createTag(TagCreateRequest model){
        var account = accountRepository.findById(model.accountId()).orElseThrow(() -> new AccountNotFoundException(model.accountId()));

        try {
            var tag = new Tag(
                    null,
                    model.tagName(),
                    LocalDateTime.now(),
                    account
            );

            var savedTag = tagRepository.save(tag);

            return new TagResponse(savedTag);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public List<TagResponse> getAllTags() {
        List<TagResponse> tags = new ArrayList<>();

        try{
            var results = tagRepository.findAll();

            if(results.isEmpty()) throw new NoRecordsFoundException();

            results.forEach(tag -> tags.add(new TagResponse(tag)));

            return tags;

        }catch (NoRecordsFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public TagResponse getTagById(Long id){
        var tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
        return new TagResponse(tag);
    }

    public TagResponse updateTag(TagUpdateRequest model){
        var tag = tagRepository.findById(model.id()).orElseThrow(() -> new TagNotFoundException(model.id()));
        var account = accountRepository.findById(model.accountId()).orElseThrow(() -> new AccountNotFoundException(model.accountId()));

        try{
            tag.setTagName(model.tagName());
            tag.setAccount(account);

            var updatedTag = tagRepository.save(tag);

            return new TagResponse(updatedTag);

        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void deleteTag(Long id){
        var tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));

        try{
            tagRepository.delete(tag);

        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

}
