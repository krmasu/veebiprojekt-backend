package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.user.PaginatedUserResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("api/user/{userId}")
    public UserDto getUserData(@PathVariable("userId") Integer userId) {
        log.info("Getting data of user with id: {}", userId);
        return userService.getUserData(userId);
    }

    @GetMapping("api/project/{projectId}/member")
    public PaginatedUserResponseDto getProjectMembers(@PathVariable("projectId") Integer projectId,
                                                      Principal principal,
                                                      Pageable pageable) {
        log.info("Getting members for project with id: {} for user with id: {}", projectId, principal.getName());
        return userService.getProjectMembers(projectId, pageable);
    }

    @PostMapping("api/project/{projectId}/member")
    public PaginatedUserResponseDto addNewProjectMembers(@PathVariable("projectId") Integer projectId,
                                                      @RequestBody AddMembersToProjectDto dto,
                                                      Pageable pageable) {
        log.info("Adding new members to project with id: {}", dto);
        return userService.addNewProjectMembers(projectId, dto, pageable);
    }
}
