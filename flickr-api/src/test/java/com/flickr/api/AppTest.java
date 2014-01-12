package com.flickr.api;

import com.flickr.api.entities.Contact;
import com.flickr.api.entities.ExifInfos;
import com.flickr.api.entities.License;
import com.flickr.api.entities.Paginated;
import com.flickr.api.entities.Photo;
import com.flickr.api.entities.PhotoPermissions;
import com.flickr.api.entities.PhotoInfos;
import com.flickr.api.entities.PhotoSize;
import com.flickr.api.entities.PhotoStats;
import com.flickr.api.entities.Photoset;
import com.flickr.api.entities.PhotosetInfos;
import com.flickr.api.entities.TotalViews;
import com.flickr.api.entities.User;
import com.flickr.api.entities.UserInfos;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApi() {
        try {
//            System.setProperty("flickr.api.debug", "true");

            Flickr flickr = new Flickr(
                    "b8b463e052bb34563b8bd2e14cd02365", "177c21b07922c7f4",
                    new File(System.getProperty("user.home"), ".flickr-api/flickr.conf"));

            if (!flickr.isLogged()) {
                String url = flickr.getAuthorizationUrl("http://localhost");
                System.out.println(url);

                String verifier = JOptionPane.showInputDialog("Verifier");
                String token = JOptionPane.showInputDialog("Token");
                flickr.verifyToken(verifier, token);
            }

            // Auth
            UserInfos caller = flickr.authenticate();
            assertNotNull(caller);
            assertNotNull(caller.getPhotosInfo().getFirstDate());
            assertTrue(caller.getPhotosInfo().getCount() > 0);

            // Favorites
            Paginated<Photo> photos = flickr.getFavoritesService().getFavorites(caller, 10, 1);
            assertFalse(photos.isEmpty());
            Photo photo = photos.get(0);
            assertNotNull(photo.getId());
            assertNotNull(photo.getImage());
            assertNotNull(photo.getOwner());
            assertNotNull(photo.getTitle());

            // Contacts
            Paginated<Contact> contacts = flickr.getContactsService().getContacts(10, 1);
            assertFalse(contacts.isEmpty());
            Contact contact = contacts.get(0);
            assertNotNull(contact.getId());
            assertNotNull(contact.getRealName());
            assertNotNull(contact.getUserName());
            assertNotNull(contact.getAvatar());

            // People
            UserInfos userInfo = flickr.getPeopleService().getUserInfo(caller);
            assertNotNull(userInfo);
            assertNotNull(userInfo.getAvatar());
            assertNotNull(userInfo.getId());
            assertNotNull(userInfo.getPhotosUrl());
            assertNotNull(userInfo.getProfileUrl());
            assertNotNull(userInfo.getRealName());
            assertNotNull(userInfo.getUserName());
            assertNotNull(userInfo.getPhotosInfo().getFirstDate());
            assertNotNull(userInfo.getPhotosInfo().getFirstDateTaken());
            assertTrue(userInfo.getPhotosInfo().getCount() > 0);

            User user = flickr.getPeopleService().findByEmail("fabien.barbero@gmail.com");
            assertNotNull(user);
            assertEquals(caller.getId(), user.getId());

            photos = flickr.getPeopleService().getUserPhotos(user, 10, 1);
            assertFalse(photos.isEmpty());

            // Photoset
            Paginated<Photoset> sets = flickr.getPhotosetsService().getPhotosets(caller, 10, 0);
            assertFalse(sets.isEmpty());
            Photoset set = sets.get(0);
            assertNotNull(set.getId());
            assertNotNull(set.getDescription());
            assertNotNull(set.getTitle());
            assertNotNull(set.getPrimaryPhoto());
            assertNotNull(set.getCreationDate());
            assertNotNull(set.getUpdateDate());
            assertTrue(set.getPhotosCount() > 0);
            assertTrue(set.getCountViews() > 0);
            
            PhotosetInfos photosetInfos = flickr.getPhotosetsService().getInfos(set);
            assertNotNull(photosetInfos);
            assertNotNull(photosetInfos.getCreateDate());
            assertNotNull(photosetInfos.getDescription());
            assertNotNull(photosetInfos.getId());
            assertNotNull(photosetInfos.getUpdateDate());
            
            flickr.getPhotosetsService().getComments(set);

            photos = flickr.getPhotosetsService().getPhotos(set, 10, 1);
            assertFalse(photos.isEmpty());

            // Photos
            photos = flickr.getPhotosService().getContactsPhotos();
            assertFalse(photos.isEmpty());

            PhotoInfos photoInfos = flickr.getPhotosService().getInfos(photo);
            assertNotNull(photoInfos);
            assertNotNull(photoInfos.getDates().getLastUpdateDate());
            assertNotNull(photoInfos.getDates().getPostedDate());
            assertNotNull(photoInfos.getDates().getTakenDate());
            assertNotNull(photoInfos.getDescription());
            assertNotNull(photoInfos.getEditability());
            assertNotNull(photoInfos.getLicense());
            assertNotNull(photoInfos.getOwner().getId());
            assertNotNull(photoInfos.getOwner().getRealName());
            assertNotNull(photoInfos.getOwner().getUserName());
            assertNotNull(photoInfos.getPublicEditability());
            assertNotNull(photoInfos.getTitle());
            assertNotNull(photoInfos.getUploadedDate());
            assertNotNull(photoInfos.getUsage());
            assertNotNull(photoInfos.getVisibility());
            assertTrue(photoInfos.getViews() > 0);

            PhotoPermissions access = flickr.getPhotosService().getPermission(photo);
            assertNotNull(access);

            photos = flickr.getPhotosService().getRecent(10, 1);
            assertFalse(photos.isEmpty());

            photos = flickr.getPhotosService().getRecentlyUpdated(10, 1);
            assertFalse(photos.isEmpty());
            photo = photos.get(0);

            List<PhotoSize> sizes = flickr.getPhotosService().getSizes(photo);
            assertFalse(sizes.isEmpty());
            
            List<License> licenses = flickr.getPhotosService().getLicenses();
            assertFalse(licenses.isEmpty());
            
            ExifInfos exifInfos = flickr.getPhotosService().getExif(photo);
            assertNotNull(exifInfos);
            assertNotNull(exifInfos.getCamera());
            assertFalse(exifInfos.getEntries().isEmpty());
            
            flickr.getPhotosService().getComments(photo);
            
            // Stats
            TotalViews views = flickr.getStatsService().getTotalViews(null);
            assertNotNull(views);
            assertTrue(views.getPhotosViews() > 0);
            assertTrue(views.getPhotosetsViews() > 0);
            
            Paginated<PhotoStats> photoStats = flickr.getStatsService().getPopularPhotos(null, 10, 1);
            assertFalse(photoStats.isEmpty());
            assertNotNull(photoStats.get(0).getPhoto());
            assertNotNull(photoStats.get(0).getStats());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }
}
