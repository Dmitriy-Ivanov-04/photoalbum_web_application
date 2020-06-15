package photoalbum.app.domain.mark;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import photoalbum.app.data.MarkStorage;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.dto.MarkJsonDTO;
import photoalbum.app.domain.mail.MailClient;
import photoalbum.app.domain.model.Mark;

@Service
public class MarkServiceDomain implements MarkService{
	
	@Autowired
	MarkStorage markStorage;
	
	@Autowired
	MailClient mailClient;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	ProfileStorage profileStorage;

	@Override
	public List<Mark> getMarkListByPhoto(Long photoId) {
		return markStorage.getMarksByPhoto(photoId);
	}

	@Override
	public void addMark(Long photoId, Long authorId, int value) {
		markStorage.add(photoId, authorId, value);
		String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
		String nicknameAuthor = profileStorage.getNicknameById(authorId);
		String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));
		
		if (!StringUtils.isEmpty(emailProfile)) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "User %s gave you a rating of %s!",
                    nicknameUser,
                    nicknameAuthor,
                    value
            );       
        	mailClient.sendMail("rfln.support@gmail.com", emailProfile, "Adding grade", message);
        }
		
	}

	@Override
	public void deleteMark(Long id) {
		markStorage.delete(id);
	}

	@Override
	public List<MarkJsonDTO> marksByPhotoAsJson(List<Mark> marks1) {
		List<Mark> marks = marks1;
		List<MarkJsonDTO> marksJson = null;
		
		if(marks != null && marks.size() > 0) {
			marksJson = new ArrayList<>(marks.size());
			for(Mark mark : marks) {
				MarkJsonDTO markDTO = new MarkJsonDTO();
				
				markDTO.setValue(mark.getValue());
				
				marksJson.add(markDTO);
			}
		}
		return marksJson;
	}

	@Override
	public void changeMark(Long photoId, Long authorId, int value) {
		markStorage.change(photoId, authorId, value);
		String emailProfile = profileStorage.findEmailById(photoStorage.getProfileIdByPhoto(photoId));
		String nicknameAuthor = profileStorage.getNicknameById(authorId);
		String nicknameUser = profileStorage.getNicknameById(photoStorage.getProfileIdByPhoto(photoId));
		
		if (!StringUtils.isEmpty(emailProfile)) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "%s changed the rating to %s",
                    nicknameUser,
                    nicknameAuthor,
                    value
            );       
        	mailClient.sendMail("rfln.support@gmail.com", emailProfile, "Password recovery", message);
        }
		
	}

	@Override
	public Mark findMarkByUserAndPhoto(Long photoId, Long profileId) {
				
		return markStorage.getMarkByPhotoAndUser(photoId, profileId);
	}
	
	
	
}