package service;

import java.util.List;

import db.enitites.Phonebook;
import models.Exceptions.BadRequestException;
import models.Exceptions.NotFoundException;
import service.repository.PhonebookRepository;

public class PhonebookService {
    PhonebookRepository phonebookRepository;
    
    public PhonebookService(PhonebookRepository phonebookRepository){
        this.phonebookRepository = phonebookRepository;
    }

    public List<Phonebook> get() throws NotFoundException {
        List<Phonebook> phonebooks = phonebookRepository.get();
        if (phonebooks.isEmpty()) {
            throw new NotFoundException("Dont´t exist any register for phonebook");
        }
        return phonebooks;
    }

    public List<Phonebook> getByIdUser(int idUser) throws NotFoundException {
        List<Phonebook> result = phonebookRepository.getByIdUser(idUser);
        if (result.isEmpty()) {
            throw new NotFoundException("Registro não encontrado para o id: " + idUser);
        }
        return result;
    }

    public void post(String name, String telefone, String email, Integer userId) throws BadRequestException {
        if (name == null || name.isBlank() || telefone == null || telefone.isBlank() || email == null || email.isBlank() || userId == null || userId < 0) {
            throw new BadRequestException("name, telefone, email e userId são obrigatórios");
        }
        phonebookRepository.create(name, telefone, email, userId);
    }

    public void put(int id, String name, String telefone, String email) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        if (name == null || name.isBlank() || telefone == null || telefone.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("Nome, telefone e email são obrigatórios");
        }
        List<Phonebook> existing = phonebookRepository.getById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("Registro não encontrado para o id: " + id);
        }
        phonebookRepository.update(id, name, telefone, email);
    }

    public void delete(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        List<Phonebook> existing = phonebookRepository.getById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("Registro não encontrado para o id: " + id);
        }
        phonebookRepository.delete(id);
    }
}
