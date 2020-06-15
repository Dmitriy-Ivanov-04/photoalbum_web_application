package photoalbum.app.domain.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import photoalbum.app.data.CommentStorage;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.dto.CommentJsonDTO;
import photoalbum.app.domain.mail.MailClient;
import photoalbum.app.domain.model.Comment;

@Service
public class CommentServiceDomain implements CommentService {
	
	@Autowired
	CommentStorage commentStorage;
	
	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	MailClient mailClient;

	@Override
	public List<Comment> getListComments(Long photoId) {
		return commentStorage.findAllComments(photoId);
	}

	@Override
	public List<CommentJsonDTO> commentsByPhotoAsJson(List<Comment> comments1) {
		List<Comment> comments = comments1;
		List<CommentJsonDTO> commentsJson = null;
		
		if(comments != null && comments.size() > 0) {
			commentsJson = new ArrayList<>(comments.size());
			for(Comment commet : comments) {
				CommentJsonDTO commetnDTO = new CommentJsonDTO();
				
				commetnDTO.setAuthorNickname(profileStorage.getNicknameById(commet.getAuthorId()));
				commetnDTO.setText(commet.getText());
				commetnDTO.setDate(commet.getDate());
				
				commentsJson.add(commetnDTO);
			}
		}
		return commentsJson;
	}

	@Override
	public void addComment(Long photoId, Long authorId, String text) {
		commentStorage.add(photoId, authorId, text);
		String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
		String nicknameAuthor = profileStorage.getNicknameById(authorId);
		String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));
		
		if (!StringUtils.isEmpty(emailProfile)) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "User %s left a comment on one of your photos: '%s'",
                    nicknameUser,
                    nicknameAuthor,
                    text
            );       
        	mailClient.sendMail("rfln.support@gmail.com", emailProfile, "Adding a comment", message);
        }
		
	}

}
