package photoalbum.app.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import photoalbum.app.domain.dto.UserAttrsJsonDTO;
import photoalbum.app.domain.mark.MarkService;
import photoalbum.app.domain.photo.PhotoService;
import photoalbum.app.domain.photo.PhotoServiceDomain;
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
	
	@Autowired
	PhotoServiceDomain photoServiceDomain;
	
	@RequestMapping(value = "/add-friend")
	public void addFriend(@RequestParam("n") String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(nickname);
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		if(loginProfileId != profileId) {
			relationshipsService.changeRelationship(loginProfileId, profileId);
		}
	}
	
	@RequestMapping(value = "/add-friend/button-text")
	public String addFriendButtonText(@RequestParam("n") String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(nickname);
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		return relationshipsService.buttonText(loginProfileId, profileId);
	}
	
	@RequestMapping(value = "/my-profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserAttrsJsonDTO> showAddFriendButton(@RequestParam("n") String nick) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean owner = false;
		String loginNick = profileDetails.getNickname();
		if(loginNick.equals(nick))
			owner = true;
		return profileService.attrsByUserAsJson(profileStorage.getIdByNickname(loginNick), owner);
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
			markService.findMarkByUserAndPhoto(photoId, authorId);
			markService.changeMark(photoId, authorId, mark);
		} catch(EmptyResultDataAccessException e) {
			markService.addMark(photoId, authorId, mark);
		}
		
		return markService.marksByPhotoAsJson(markStorage.getMarksByPhoto(photoId));
	}
	
	@RequestMapping(value = "/comments/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CommentJsonDTO> addComment(@RequestParam("id") Long photoId, @RequestParam("t") String text) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commentService.addComment(photoId, profileStorage.getIdByNickname(profileDetails.getNickname()), text);
		return commentService.commentsByPhotoAsJson(commentStorage.getCommentsByPhoto(photoId));
	}
	
	@RequestMapping(value = "/photos/copy")
    public void copyPhoto(@RequestParam("id") Long photoId) throws IOException {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        photoServiceDomain.copyPhoto(profileStorage.getIdByNickname(profileDetails.getNickname()), photoId);
    }
	
	@RequestMapping(value = "/photos/delete")
    public void deletePhoto(@RequestParam("id") Long photoId) throws IOException {
        photoServiceDomain.deletePhoto(photoId);
    }
	
	@RequestMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AlbumJsonDTO> albumList(@RequestParam("n") String nick) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick));
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		return albumService.albumsByUserAsJson(albumStorage.findAlbumsByUser(profileId, relationshipsService.getAccesLevel(profileId, loginProfileId)));
	}
	
	@RequestMapping(value = "/albums/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAlbum(@RequestParam("a") String albumName) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		albumService.deleteAlbum(profileId, albumName);
	}
	
	@RequestMapping(value = "/photos/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PhotoJsonDTO> photoListByAlbum(@PathVariable Long albumId) {
		return photoService.photosByUserAsJson(photoStorage.getPhotosByAlbum(albumId));
	}
	
	@RequestMapping(value = "/albums/create")
    public void createAlbum(@RequestParam("n") String name, @RequestParam("l") int accesLevel) throws IOException {
        ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        albumStorage.insert(profileStorage.getIdByNickname(profileDetails.getNickname()), name, accesLevel);
    }
	
	@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PhotoJsonDTO> searchByParametrs(@RequestParam("q") String query, @RequestParam("r") int rating, @RequestParam("d") String date) {
		return photoService.photosByUserAsJson(photoService.searchByParametrs(query, rating, date));
	}
	
	@RequestMapping(value = "/search/tag", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PhotoJsonDTO> searchByTag(@RequestParam("t") String tag) {
		return photoService.photosByUserAsJson(photoService.searchByTag(tag));
	}
}
