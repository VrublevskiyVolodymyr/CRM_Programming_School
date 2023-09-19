package ua.com.owu.crm_programming_school.services.groupsService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.owu.crm_programming_school.dao.GroupDAO;
import ua.com.owu.crm_programming_school.models.Group;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupsServiceImpl1 implements GroupsService{
    private GroupDAO groupDAO;

    @Override
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupDAO.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Group> createGroup(Group group) {
        Group savedGroup = groupDAO.save(new Group(group.getName()));
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }


}
