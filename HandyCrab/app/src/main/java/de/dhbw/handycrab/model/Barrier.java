package de.dhbw.handycrab.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import org.bson.types.ObjectId;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class Barrier implements Votable {

    private ObjectId _id;
    private ObjectId userId;
    private String title;
    private double longitude;
    private double latitude;
    private String picturePath;
    private String description;
    private String postcode;
    private List<Solution> solutions = new ArrayList<>();
    private int upVotes;
    private int downVotes;
    private Vote vote;
    private Bitmap imageBitmap;
    private float distance;

    public Barrier() {
    }

    public Barrier(ObjectId id, ObjectId userId, String title, double longitude, double latitude, String picturePath, String description, String postcode, List<Solution> solutions, int upvotes, int downvotes, Vote vote) {
        _id = id;
        this.userId = userId;
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        this.picturePath = picturePath;
        this.description = description;
        this.postcode = postcode;
        this.solutions = solutions;
        this.upVotes = upvotes;
        this.downVotes = downvotes;
        this.vote = vote;
    }

    public ObjectId getId() {
        return _id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getPicture() {
        return picturePath;
    }

    public String getDescription() {
        return description;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public boolean downloadImage() {
        if (picturePath == null || picturePath.isEmpty()) {
            return false;
        }
        if (imageBitmap == null) {
            try {
                InputStream in = new URL(picturePath).openStream();
                imageBitmap = BitmapFactory.decodeStream(in);
            }
            catch (RuntimeException ignored) {
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void setImageBitmapCallback(BiConsumer<Boolean, Bitmap> function) {
        try {
            Boolean success = CompletableFuture.supplyAsync(() -> downloadImage()).get();
            function.accept(success, imageBitmap);
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setDistanceTo(Location distLoc){
        Location barrierLoc = new Location("barrierLoc");
        barrierLoc.setLatitude(this.latitude);
        barrierLoc.setLongitude(this.longitude);
        distance =  barrierLoc.distanceTo(distLoc);
    }

    public float getDistance() {
        return distance;
    }
}
