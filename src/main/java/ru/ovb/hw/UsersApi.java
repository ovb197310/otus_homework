package ru.ovb.hw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UsersApi {

    @Autowired
    @Qualifier("dbUserStore")
    private IUserStore userStore;


    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getById(@PathVariable(value = "userId") Long userId) {
        User findUser = userStore.find(userId);
        if (findUser != null) {
            return ResponseEntity.ok(findUser);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<User> deleteById(@PathVariable(value = "userId") Long userId) {
        User delUser = userStore.delete(userId);
        if (delUser != null) {
            return ResponseEntity.ok(delUser);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> save(@RequestBody() User user) {
        try {
            Long id = userStore.save(user);
            user.setId(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorInfo", ex.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/users", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> update(@RequestBody() User user) {
        try {
            User prevValue = userStore.update(user);
            if (prevValue == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(prevValue);
        } catch (IllegalArgumentException ex) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorInfo", ex.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
