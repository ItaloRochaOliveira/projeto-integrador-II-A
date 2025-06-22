package service;

import models.Exceptions.BadRequestException;
import models.Exceptions.NotFoundException;
import service.repository.UserRepository;
import db.enitites.User;
import java.util.List;

public class UserService{
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> get(int id) throws NotFoundException {
        List<User> result = userRepository.getBy(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        return result;
    }

    public List<User> getAll() {
        return userRepository.get();
    }

    public void post(String name, String email) throws BadRequestException {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("name e email são obrigatórios");
        }
        userRepository.create(name, email);
    }

    public void put(int id, String name, String email) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("Nome e email são obrigatórios");
        }
        List<User> existing = userRepository.getBy(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.update(id, name, email);
    }

    public void delete(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        List<User> existing = userRepository.getBy(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.delete(id);
    }
}