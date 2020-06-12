package photoalbum.app.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.data.CommentStorage;
import photoalbum.app.data.MarkStorage;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.data.TagStorage;
import photoalbum.app.domain.album.AlbumService;
import photoalbum.app.domain.comment.CommentService;
import photoalbum.app.domain.dto.AlbumJsonDTO;
import photoalbum.app.domain.dto.CommentJsonDTO;
import photoalbum.app.domain.dto.MarkJsonDTO;
import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.dto.ProfileJsonDTO;
import photoalbum.app.domain.dto.TagJsonDTO;
import photoalbum.app.domain.mark.MarkService;
import photoalbum.app.domain.model.Photo;
import photoalbum.app.domain.model.Relationships;
import photoalbum.app.domain.model.Status;
import photoalbum.app.domain.photo.PhotoService;
import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.domain.relationships.RelationshipsService;
import photoalbum.app.domain.tag.TagService;
import photoalbum.app.spring.ProfileDetailsImpl;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	@Autowired
	RelationshipsStorage relationshipsStorage;
	
	@Autowired
	RelationshipsService relationshipsService;
	
	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	PhotoService photoService;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	TagStorage tagStorage;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	CommentStorage commentStorage;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	MarkService markService;
	
	@Autowired
	MarkStorage markStorage;
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	AlbumStorage albumStorage;
	
	@Value("${project.manager.photo.dir.path}")
    private String photoDirPath;
	
	@RequestMapping(value = "/add-friend")
	public void addFriend(@RequestParam("n") String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long profileId = profileStorage.getIdByNickname(nickname);
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		if(loginProfileId != profileId) {
			Relationships relationship;
			try {
				relationship = relationshipsStorage.findRelationshipsByUsers(loginProfileId, profileId);
				Status status = relationship.getStatus();
				if(status == Status.SUBSCRIBER)
					relationshipsStorage.unsubscribe(relationship.getId());
				if (status == Status.FRIEND)
					relationshipsStorage.deleteFriend(profileId, loginProfileId, relationship.getId());
			} catch(EmptyResultDataAccessException e) {
				try {
					relationship = relationshipsStorage.findRelationshipsByUsers(profileId, loginProfileId);
					Status status = relationship.getStatus();
					if(status == Status.SUBSCRIBER)
						relationshipsStorage.acceptInvite(relationship.getId());
					if (status == Status.FRIEND)
						relationshipsStorage.deleteFriend(profileId, loginProfileId, relationship.getId());
				} catch(EmptyResultDataAccessException e1) {
					relationshipsStorage.sendInvite(loginProfileId, profileId);
				}
			}
		}
	}
	
	@RequestMapping(value = "/my-profile")
	public boolean showAddFriendButton(@RequestParam("n") String nick) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(profileDetails.getNickname().equals(nick))	
			return true;
		else
			return false;
	}
	
	@RequestMapping(value = "/friend-list/{divId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProfileJsonDTO> friendList(@RequestParam("n") String nick, @PathVariable String divId) {
		return profileService.usersByUserAsJson(profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick)), HtmlUtils.htmlEscape(divId));
	}
	
	@RequestMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PhotoJsonDTO> photoList(@RequestParam("n") String nick) {
		//return photoService.photosByUserAsJson(profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick)));
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		return photoService.photosByUserAsJson(photoStorage.getPhotosByUser(profileId, relationshipsService.getAccesLevel(profileId, loginProfileId)));
	}
	
	@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TagJsonDTO> tagList(@RequestParam("id") Long photoId) {
		//return tagService.tagsByUserAsJson(tagStorage.getTagsByPhoto(photoId));
		return tagService.tagsByPhotoAsJson(tagStorage.getTagsByPhoto(photoId));
	}
	
	@RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CommentJsonDTO> commentList(@RequestParam("id") Long photoId) {
		return commentService.commentsByPhotoAsJson(commentStorage.getCommentsByPhoto(photoId));
	}
	
	@RequestMapping(value = "/marks", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MarkJsonDTO> markList(@RequestParam("id") Long photoId) {
		return markService.marksByPhotoAsJson(markStorage.getMarksByPhoto(photoId));
	}
	
	@RequestMapping(value = "/marks/rate", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MarkJsonDTO> rate(@RequestParam("m") int mark, @RequestParam("id") Long photoId) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long authorId = profileStorage.getIdByNickname(profileDetails.getNickname());
		try {
			markStorage.getMarkByPhotoAndUser(photoId, authorId);
			markStorage.change(photoId, authorId, mark);
		}catch(EmptyResultDataAccessException e) {
			markStorage.add(photoId, authorId, mark);
		}
		return markService.marksByPhotoAsJson(markStorage.getMarksByPhoto(photoId));
	}
	
	@RequestMapping(value = "/comments/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CommentJsonDTO> addComment(@RequestParam("id") Long photoId, @RequestParam("t") String text) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commentStorage.add(photoId, profileStorage.getIdByNickname(profileDetails.getNickname()), text);
		return commentService.commentsByPhotoAsJson(commentStorage.getCommentsByPhoto(photoId));
	}
	
	/*@RequestMapping(value = "/photos/copy")
	public void copyPhoto(@RequestParam("id") Long photoId) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commentStorage.add(photoId, profileStorage.getIdByNickname(profileDetails.getNickname()), text);
	}*/
	
	@RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AlbumJsonDTO> albumList(@RequestParam("n") String nick) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		return albumService.albumsByUserAsJson(albumStorage.findAlbumsByUser(profileId, relationshipsService.getAccesLevel(profileId, loginProfileId)));
	}
	
	@RequestMapping(value = "/photos/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PhotoJsonDTO> photoListByAlbum(@PathVariable Long albumId) {
		return photoService.photosByUserAsJson(photoStorage.getPhotosByAlbum(albumId));
	}
}
