package com.example.retea_gui.domain.validators;

import com.example.retea_gui.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException{
        String msg = "";
        if(entity.getVarsta() < 18){
            msg += "Varsta trebuie sa fie de cel putin 14 ani!\n";
        }
        //if(!entity.getNume().toUpperCase().equals(entity.getNume())){
        //    msg += "Numele trebuie sa fie scris cu litere mari!\n";
        //}
        //if(!entity.getPrenume().toUpperCase().equals(entity.getPrenume())){
        //    msg += "Prenumele trebuie sa fie scris cu litere mari!\n";
        //}
        if(entity.getNume().length() < 3){
            msg += "Numele trebuie sa contina cel putin 3 litere!";
        }

        if(!msg.equals("")){
            throw new ValidationException(msg);
        }
    }
}
