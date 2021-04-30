package by.golik.dealerstat;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.repository.CommentRepository;
import by.golik.dealerstat.repository.UnconfirmedCommentRepository;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.impl.CommentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Golik
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UnconfirmedCommentRepository unconfirmedCommentRepository;

    private CommentServiceImpl commentService;

    private Optional<Comment> testComment;

    @Before
    public void init() {
        commentService = new CommentServiceImpl(commentRepository,
                unconfirmedCommentRepository);
        testComment = Optional.of(new Comment("Message", 4,
                true, new GameObject(), new User()));
        when(commentRepository.findById(1)).thenReturn(testComment);
    }

    @Test
    public void getCommentTest() throws ResourceNotFoundException {
        Comment testComment = commentService.getComment(1);
        assertNotNull(testComment);
    }

    @Test
    public void updateCommentTest() {
        Comment adminComment = new Comment("Old message", 5 , true,
                new GameObject(), new User());
        Comment nonAdminComment = new Comment("Old message", 5 , true,
                new GameObject(), new User());
        CommentDTO commentDTO = new CommentDTO(1, "New message", 5,
                Calendar.getInstance(), Calendar.getInstance(),
                1, 1);

        commentService.updateComment(adminComment, commentDTO, true);
        commentService.updateComment(nonAdminComment, commentDTO, false);
        assertEquals(adminComment.getMessage(), "New message");
        assertEquals(nonAdminComment.getMessage(), "Old message");
    }
}
